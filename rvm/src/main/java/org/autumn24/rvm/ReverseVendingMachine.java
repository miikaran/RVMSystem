/*
 * This file is part of RVMSystem.
 *
 * RVMSystem is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * RVMSystem is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with RVMSystem
 * If not, see <https://www.gnu.org/licenses/>.
 */

package org.autumn24.rvm;

import org.autumn24.charity.Charity;
import org.autumn24.charity.CharityFactory;
import org.autumn24.exceptions.InvalidItemMaterialException;
import org.autumn24.exceptions.MissingItemMaterialException;
import org.autumn24.extra.Donate;
import org.autumn24.extra.Recycle;
import org.autumn24.items.Item;
import org.autumn24.items.ItemMaterial;
import org.autumn24.items.ItemStatus;
import org.autumn24.rvm.enums.ReverseVendingMachineFunctionalStatus;
import org.autumn24.rvm.enums.ReverseVendingMachinePowerStatus;
import org.autumn24.rvm.enums.ReverseVendingMachineStatus;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A class that represents a reverse vending machine.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class ReverseVendingMachine implements Recycle, Donate {

	private final String rvmId;
	public short ALUMINIUM_CANS_LIMIT = 3;
	public short GLASS_BOTTLES_LIMIT = 3;
	public short PLASTIC_BOTTLES_LIMIT = 3;
	public BigDecimal recyclingSessionTotalValue;
	public short recyclingSessionRecycledAmount;

	// Keeps count of recycled items
	public short numberOfAluminiumCansRecycled;
	public short numberOfGlassBottlesRecycled;
	public short numberOfPlasticBottlesRecycled;

	// Keeps count of recyclable specific limits
	public short aluminiumCanLimitCounter;
	public short glassBottleLimitCounter;
	public short plasticBottleLimitCounter;

	public ReverseVendingMachineStatus rvmStatus;
	public ReverseVendingMachineFunctionalStatus rvmFnStatus;
	public ReverseVendingMachinePowerStatus rvmPwStatus;

	public ReverseVendingMachine() {
		rvmId = UUID.randomUUID().toString();
		rvmFnStatus = ReverseVendingMachineFunctionalStatus.OPERATIONAL;
		rvmPwStatus = ReverseVendingMachinePowerStatus.OFF;
	}

	public String getRvmId() {
		return rvmId;
	}

	@Override
	public boolean recycleItem(Item item) {
		ItemStatus status = item.getItemStatus();
		ItemMaterial material = item.getItemMaterial();
		if (!validateRecyclableItem(status, material)) {
			return false;
		}
		BigDecimal value = item.getDeterminedValue();
		boolean limitReached;
		RecyclingPile pile = switch (material) {
			case ALUMINIUM -> {
				limitReached = IsAluminiumLimitReached();
				yield RecyclingPile.METAL;
			}
			case GLASS -> {
				limitReached = IsGlassBottleLimitReached();
				yield RecyclingPile.GLASS;
			}
			case PLASTIC -> {
				limitReached = IsPlasticBottleLimitReached();
				yield RecyclingPile.PLASTIC;
			}
			default -> throw new InvalidItemMaterialException("Material'" + material + "' not found in RecyclingPile.");
		};
		if (limitReached) {
			rvmStatus = ReverseVendingMachineStatus.FULL;
			return false;
		}
		if (wrinkledItemDetected(item)) {
			return false;
		}
		increaseRecycledItemsCounter(pile);
		increaseSessionCounters(value);
		return true;
	}

	@Override
	public void donateToChosenCharity(Charity charity) {
		System.out.printf("Donated %s to %s%n", recyclingSessionTotalValue, charity.name());
		resetSessionCounters();
	}

	public Receipt printReceipt() {
		Receipt receipt = new Receipt(
				numberOfAluminiumCansRecycled,
				numberOfGlassBottlesRecycled,
				numberOfPlasticBottlesRecycled,
				recyclingSessionTotalValue
		);
		receipt.displayReceipt();
		resetSessionCounters();
		return receipt;
	}

	public Charity donateToCharity(int charityIndex) {
		Charity charity = CharityFactory.createCharity(charityIndex);
		System.out.println("Donating " + recyclingSessionTotalValue + "€ to " + charity.name());
		System.out.println("Thank you for choosing us!");
		return charity;
	}

	public boolean validateRecyclableItem(ItemStatus status, ItemMaterial material) {
		if (material == null) {
			throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
		}
		return !status.equals(ItemStatus.WRINKLED);
	}

	public void increaseRecycledItemsCounter(RecyclingPile pile) {
		switch (pile) {
			// Increase number of type X recycled item counters.
			case METAL: {
				numberOfAluminiumCansRecycled++;
				aluminiumCanLimitCounter++;
				break;
			}
			case GLASS: {
				numberOfGlassBottlesRecycled++;
				glassBottleLimitCounter++;
				break;
			}
			case PLASTIC: {
				numberOfPlasticBottlesRecycled++;
				plasticBottleLimitCounter++;
				break;
			}
		}
	}

	public void increaseSessionCounters(BigDecimal value) {
		if (recyclingSessionTotalValue == null) {
			recyclingSessionTotalValue = BigDecimal.ZERO;
		}
		recyclingSessionTotalValue = recyclingSessionTotalValue.add(value);
		recyclingSessionRecycledAmount++;
	}

	public void startMachine() {
		System.out.println("\n\uD83D\uDD0B Starting machine: " + rvmId);
		rvmPwStatus = ReverseVendingMachinePowerStatus.ON;
	}

	public void exitFromSleepMode() {
		if (rvmStatus.equals(ReverseVendingMachineStatus.IDLE)) {
			System.out.println("\n\uD83D\uDD0B Machine: " + rvmId + " recovering from sleep mode:");
			rvmStatus = ReverseVendingMachineStatus.IN_USE;
		}
	}

	public boolean isValidSleepModeException(Exception e) {
		// Used to identify exceptions that can be suppressed when recovering from sleepmode
		return e instanceof IllegalArgumentException && ReverseVendingMachineStatus.IDLE.equals(rvmStatus);
	}

	public boolean machineIsUsable() {
		return ReverseVendingMachineFunctionalStatus.OPERATIONAL.equals(rvmFnStatus)
				&& ReverseVendingMachinePowerStatus.ON.equals(rvmPwStatus)
				&& !ReverseVendingMachineStatus.FULL.equals(rvmStatus);
	}

	public boolean IsMachineFull() {
		return ReverseVendingMachineStatus.FULL.equals(rvmStatus);
	}

	public String getFullPile() {
		if (IsAluminiumLimitReached()) {
			return RecyclingPile.METAL.name();
		} else if (IsPlasticBottleLimitReached()) {
			return RecyclingPile.PLASTIC.name();
		} else if (IsGlassBottleLimitReached()) {
			return RecyclingPile.GLASS.name();
		}
		return "";
	}

	public boolean IsAluminiumLimitReached() {
		return ALUMINIUM_CANS_LIMIT == aluminiumCanLimitCounter;
	}

	public boolean IsPlasticBottleLimitReached() {
		return PLASTIC_BOTTLES_LIMIT == plasticBottleLimitCounter;
	}

	public boolean IsGlassBottleLimitReached() {
		return GLASS_BOTTLES_LIMIT == glassBottleLimitCounter;
	}

	public boolean wrinkledItemDetected(Item item) {
		if (item == null) {
			return false;
		}
		return ItemStatus.WRINKLED.equals(item.getItemStatus());
	}

	public void resetSessionCounters() {
		recyclingSessionTotalValue = BigDecimal.ZERO;
		recyclingSessionRecycledAmount = 0;
	}
}
