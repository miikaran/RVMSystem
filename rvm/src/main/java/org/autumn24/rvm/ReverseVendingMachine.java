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
 */
public class ReverseVendingMachine implements Recycle, Donate {

	/**
	 * Enum mapping that stores recycled items in its corresponding material key.
	 */
	public final Map<ItemMaterial, RecyclableData> recyclables = new EnumMap<>(ItemMaterial.class);
	/**
	 * Stores recycling session specific data that will be reset after every session.
	 */
	public transient final RecyclingSessionData recyclingSession;
	/**
	 * Unique id of the reverse vending machine.
	 */
	private final String rvmId;
	/**
	 * Static mapping of item material to material pile.
	 */
	private transient final Map<ItemMaterial, RecyclingPile> materialToPileMap = Map.of(
			ItemMaterial.GLASS, RecyclingPile.GLASS,
			ItemMaterial.ALUMINIUM, RecyclingPile.METAL,
			ItemMaterial.PLASTIC, RecyclingPile.PLASTIC
	);
	/**
	 * Reverse vending machine functional status (BROKEN, OPERATIONAL).
	 */
	private final transient ReverseVendingMachineFunctionalStatus rvmFnStatus;
	/**
	 * Reverse vending machine power status (ON, OFF).
	 */
	private transient ReverseVendingMachinePowerStatus rvmPwStatus;
	/**
	 * Reverse vending machine status (IDLE, IN-USE, FULL).
	 */
	private transient ReverseVendingMachineStatus rvmStatus;


	/**
	 * Creates a new reverse vending machine with default values.
	 */
	public ReverseVendingMachine() {
		rvmId = UUID.randomUUID().toString();
		rvmFnStatus = ReverseVendingMachineFunctionalStatus.OPERATIONAL;
		rvmPwStatus = ReverseVendingMachinePowerStatus.OFF;
		recyclingSession = new RecyclingSessionData();
	}

	/**
	 * Gets reverse vending machines id.
	 *
	 * @return A string representing the id.
	 */
	public String getRvmId() {
		return rvmId;
	}

	/**
	 * Recycles the given item to a matching recycling pile.
	 *
	 * @param item The item to be recycled.
	 * @return Boolean value of the process success.
	 */
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

	/**
	 * Donates current recycling sessions total value to chosen charity.
	 *
	 * @param charity The chosen charity to donate to.
	 */
	@Override
	public void donateToChosenCharity(Charity charity) {
		System.out.printf("Donated %s to %s%n", recyclingSession.getRecyclingSessionTotalValue(), charity.name());
	}

	/**
	 * Generates a receipt from current recycling sessions data.
	 *
	 * @return The generated receipt.
	 */
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

	/**
	 * Donates current recycling session total value to chosen charity by index.
	 *
	 * @param charityIndex Index of the chosen charity.
	 * @return The chosen charity to donate to.
	 */
	public Charity donateToCharity(int charityIndex) {
		Charity charity = CharityFactory.createCharity(charityIndex);
		System.out.println("Donating " + recyclingSession.getRecyclingSessionTotalValue() + "€ to " + charity.name());
		System.out.println("Thank you for choosing us!");
		return charity;
	}

	/**
	 * Validates a recyclable item.
	 *
	 * @param status   Status of the item
	 * @param material Material of the item
	 * @return Boolean value representing is the item good to recycle.
	 */
	public boolean validateRecyclableItem(ItemStatus status, ItemMaterial material) {
		if (material == null) {
			throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
		}
		return !status.equals(ItemStatus.WRINKLED);
	}

	/**
	 * Increases the recycled items counter by one for the stored recyclable data by material.
	 *
	 * @param material Material of the recycled item
	 */
	public void increaseRecycledItemsCounter(ItemMaterial material) {
		if (material == null) {
			throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
		}
		RecyclableData recyclable = recyclables.get(material);
		recyclable.addTotalRecycled(1);
		recyclable.addToRecyclingLimitCounter(1);
		recyclable.addSessionRecycled(1);
	}

	/**
	 * Increases session specific counters using the recyclingSession.
	 *
	 * @param material The material of the recycled item.
	 * @param value    The value of the recycled item.
	 */
	public void increaseSessionCounters(ItemMaterial material, BigDecimal value) {
		BigDecimal currTotalValue = recyclingSession.getRecyclingSessionTotalValue();
		if (currTotalValue == null) {
			recyclingSession.setRecyclingSessionTotalValue(BigDecimal.ZERO);
		}
		recyclingSession.addRecyclable(material, (short) 1, value);
	}

	/**
	 * Starts the initialized RVM by setting the power status to ON.
	 */
	public void startMachine() {
		System.out.println("\n\uD83D\uDD0B Starting machine: " + rvmId);
		rvmPwStatus = ReverseVendingMachinePowerStatus.ON;
	}

	/**
	 * Recovers the machine from sleep-mode. Sets status back to IN_USE.
	 */
	public void exitFromSleepMode() {
		if (getRvmStatus().equals(ReverseVendingMachineStatus.IDLE)) {
			System.out.println("\n\uD83D\uDD0B Machine: " + rvmId + " recovering from sleep mode:");
			setRvmStatus(ReverseVendingMachineStatus.IN_USE);
		}
	}

	/**
	 * Checks whether invalid option exception is raised during the machine was in sleep-mode.
	 *
	 * @param e The raised exception
	 * @return Boolean value representing is the correct exception raised during sleep-mode.
	 */
	public boolean isValidSleepModeException(Exception e) {
		// Used to identify exceptions that can be suppressed when recovering from sleepmode
		return e instanceof InvalidOptionException && ReverseVendingMachineStatus.IDLE.equals(getRvmStatus());
	}

	/**
	 * Checks whether the machine is usable by checking its statuses (Functional, Power, Default).
	 *
	 * @return Boolean value representing is the machine usable.
	 */
	public boolean machineIsUsable() {
		return ReverseVendingMachineFunctionalStatus.OPERATIONAL.equals(rvmFnStatus)
				&& ReverseVendingMachinePowerStatus.ON.equals(rvmPwStatus)
				&& !ReverseVendingMachineStatus.FULL.equals(getRvmStatus());
	}

	/**
	 * Checks whether the machine is full or not. Checks it from the RVM status.
	 *
	 * @return Boolean value representing is it full or not.
	 */
	public boolean IsMachineFull() {
		return ReverseVendingMachineStatus.FULL.equals(getRvmStatus());
	}

	/**
	 * Gets the first full pile that is countered.
	 *
	 * @return The full pile.
	 */
	public String getFullPile() {
		return materialToPileMap.entrySet().stream()
				.filter(entry -> recyclables.get(entry.getKey()).isLimitReached())
				.map(entry -> entry.getValue().name())
				.findFirst()
				.orElse("");
	}

	/**
	 * Checks whether the recycled item is wrinkled or not.
	 *
	 * @param item Recycled item.
	 * @return Boolean value representing is the item wrinkled or not.
	 */
	public boolean wrinkledItemDetected(Item item) {
		if (item == null) {
			return false;
		}
		return ItemStatus.WRINKLED.equals(item.getItemStatus());
	}

	/**
	 * Resets session counters to their starting values.
	 * Used at the end of a session to make sure that next recycler has a fresh start.
	 */
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

	/**
	 * Gets reverse vending machine status.
	 *
	 * @return Reverse vending machine status.
	 */
	public ReverseVendingMachineStatus getRvmStatus() {
		return rvmStatus;
	}

	/**
	 * Sets reverse vending machine status
	 *
	 * @param rvmStatus Status to be set
	 */
	public void setRvmStatus(ReverseVendingMachineStatus rvmStatus) {
		this.rvmStatus = rvmStatus;
	}
}
