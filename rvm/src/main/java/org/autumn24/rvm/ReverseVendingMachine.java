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

import org.autumn24.UserInterface;
import org.autumn24.charity.Charity;
import org.autumn24.charity.CharityFactory;
import org.autumn24.data.RecyclableData;
import org.autumn24.data.RecyclingSessionData;
import org.autumn24.exceptions.InvalidOptionException;
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
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * A class that represents a reverse vending machine.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class ReverseVendingMachine implements Recycle, Donate {

	public final Map<ItemMaterial, RecyclableData> recyclables = new EnumMap<>(ItemMaterial.class);
	public transient final RecyclingSessionData recyclingSession;
	private final String rvmId;
	private transient final Map<ItemMaterial, RecyclingPile> materialToPileMap = Map.of(
			ItemMaterial.GLASS, RecyclingPile.GLASS,
			ItemMaterial.ALUMINIUM, RecyclingPile.METAL,
			ItemMaterial.PLASTIC, RecyclingPile.PLASTIC
	);
	private final transient ReverseVendingMachineFunctionalStatus rvmFnStatus;
	private transient ReverseVendingMachinePowerStatus rvmPwStatus;
	private transient ReverseVendingMachineStatus rvmStatus;

	public ReverseVendingMachine() {
		rvmId = UUID.randomUUID().toString();
		rvmFnStatus = ReverseVendingMachineFunctionalStatus.OPERATIONAL;
		rvmPwStatus = ReverseVendingMachinePowerStatus.OFF;
		recyclingSession = new RecyclingSessionData();
	}

	public String getRvmId() {
		return rvmId;
	}

	@Override
	public boolean recycleItem(Item item) {
		ItemStatus status = item.getItemStatus();
		ItemMaterial material = item.getItemMaterial();
		if (!validateRecyclableItem(status, material) || wrinkledItemDetected(item)) {
			return false;
		}
		BigDecimal value = item.getDeterminedValue();
		RecyclableData recyclableData = recyclables.get(material);
		if (recyclableData.isLimitReached()) {
			setRvmStatus(ReverseVendingMachineStatus.FULL);
			return false;
		}
		RecyclingPile pile = materialToPileMap.get(material);
		System.out.println("Sorting item to " + pile.name());
		increaseRecycledItemsCounter(material);
		increaseSessionCounters(material, value);
		return true;
	}

	@Override
	public void donateToChosenCharity(Charity charity) {
		System.out.printf("Donated %s to %s%n", recyclingSession.getRecyclingSessionTotalValue(), charity.name());
	}

	public Receipt printReceipt() {
		Receipt receipt = new Receipt(
				recyclables.get(ItemMaterial.ALUMINIUM).getSessionRecycled(),
				recyclables.get(ItemMaterial.GLASS).getSessionRecycled(),
				recyclables.get(ItemMaterial.PLASTIC).getSessionRecycled(),
				recyclingSession.getRecyclingSessionTotalValue()
		);
		UserInterface.displayReceipt(receipt);
		return receipt;
	}

	public Charity donateToCharity(int charityIndex) {
		Charity charity = CharityFactory.createCharity(charityIndex);
		System.out.println("Donating " + recyclingSession.getRecyclingSessionTotalValue() + "€ to " + charity.name());
		System.out.println("Thank you for choosing us!");
		return charity;
	}

	public boolean validateRecyclableItem(ItemStatus status, ItemMaterial material) {
		if (material == null) {
			throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
		}
		return !status.equals(ItemStatus.WRINKLED);
	}

	public void increaseRecycledItemsCounter(ItemMaterial material) {
		if (material == null) {
			throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
		}
		RecyclableData recyclable = recyclables.get(material);
		recyclable.addTotalRecycled(1);
		recyclable.addToRecyclingLimitCounter(1);
		recyclable.addSessionRecycled(1);
	}

	public void increaseSessionCounters(ItemMaterial material, BigDecimal value) {
		BigDecimal currTotalValue = recyclingSession.getRecyclingSessionTotalValue();
		if (currTotalValue == null) {
			recyclingSession.setRecyclingSessionTotalValue(BigDecimal.ZERO);
		}
		recyclingSession.addRecyclable(material, (short) 1, value);
	}

	public void startMachine() {
		System.out.println("\n\uD83D\uDD0B Starting machine: " + rvmId);
		rvmPwStatus = ReverseVendingMachinePowerStatus.ON;
	}

	public void exitFromSleepMode() {
		if (getRvmStatus().equals(ReverseVendingMachineStatus.IDLE)) {
			System.out.println("\n\uD83D\uDD0B Machine: " + rvmId + " recovering from sleep mode:");
			setRvmStatus(ReverseVendingMachineStatus.IN_USE);
		}
	}

	public boolean isValidSleepModeException(Exception e) {
		// Used to identify exceptions that can be suppressed when recovering from sleepmode
		return e instanceof InvalidOptionException && ReverseVendingMachineStatus.IDLE.equals(getRvmStatus());
	}

	public boolean machineIsUsable() {
		return ReverseVendingMachineFunctionalStatus.OPERATIONAL.equals(rvmFnStatus)
				&& ReverseVendingMachinePowerStatus.ON.equals(rvmPwStatus)
				&& !ReverseVendingMachineStatus.FULL.equals(getRvmStatus());
	}

	public boolean IsMachineFull() {
		return ReverseVendingMachineStatus.FULL.equals(getRvmStatus());
	}

	public String getFullPile() {
		return materialToPileMap.entrySet().stream()
				.filter(entry -> recyclables.get(entry.getKey()).isLimitReached())
				.map(entry -> entry.getValue().name())
				.findFirst()
				.orElse("");
	}

	public boolean wrinkledItemDetected(Item item) {
		if (item == null) {
			return false;
		}
		return ItemStatus.WRINKLED.equals(item.getItemStatus());
	}

	public void resetSessionCounters() {
		recyclingSession.setRecyclingSessionTotalValue(BigDecimal.ZERO);
		recyclingSession.setRecyclingSessionRecycledAmount((short) 0);
		recyclingSession.getSessionRecycledAmounts().clear();
		recyclables.forEach((material, recyclable) -> recyclable.setSessionRecycled((short) 0));
		recyclingSession.setRecyclingSessionRecycledAluminumBottles(0);
		recyclingSession.setRecyclingSessionRecycledPlasticBottles(0);
		recyclingSession.setRecyclingSessionRecycledGlassBottles(0);
	}

	@Override
	public String toString() {
		return "ReverseVendingMachine{" +
				"recyclables=" + recyclables +
				", recyclingSession=" + recyclingSession +
				", rvmId='" + rvmId + '\'' +
				", materialToPileMap=" + materialToPileMap +
				", rvmStatus=" + getRvmStatus() +
				", rvmFnStatus=" + rvmFnStatus +
				", rvmPwStatus=" + rvmPwStatus +
				'}';
	}

	public ReverseVendingMachineStatus getRvmStatus() {
		return rvmStatus;
	}

	public void setRvmStatus(ReverseVendingMachineStatus rvmStatus) {
		this.rvmStatus = rvmStatus;
	}
}
