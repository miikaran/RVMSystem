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

package org.autumn24;

import org.autumn24.excpetion.InvalidItemSizeException;
import org.autumn24.items.Item;
import org.autumn24.items.ItemFactory;
import org.autumn24.items.ItemStatus;
import org.autumn24.rvm.InactivityTimer;
import org.autumn24.rvm.ReverseVendingMachine;
import org.autumn24.rvm.ReverseVendingMachineStatus;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that manages main actions in the application.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class ApplicationManager {

	public static final Scanner scanner = new Scanner(System.in);
	private static final ReverseVendingMachine rvm = new ReverseVendingMachine();
	private final InactivityTimer inactivityTimer;
	private final ArrayList<Item> items = new ArrayList<>();
	public boolean appRunning = false;

	public ApplicationManager() {
		this.inactivityTimer = new InactivityTimer(rvm);
		generateBottles(new ItemFactory());
		inactivityTimer.resetTimer();
	}

	public static int getUserAction() {
		if (!scanner.hasNextInt()) {
			scanner.nextLine(); // Clear invalid input
			return 0;
		}
		int choice = scanner.nextInt();
		scanner.nextLine(); // Clear input buffer for new line
		if (ReverseVendingMachineStatus.IDLE.equals(rvm.rvmStatus)) {
            /*
            If RVM has gone to sleep mode while waiting on user action,
            return invalid option to wake activate sleep-mode recovery.
            */
			return 0;
		}
		return choice;
	}

	public void generateBottles(ItemFactory itemFactory) {
		System.out.println("\nGenerated bottles: ");
		for (int i = 0; i < 8; i++) {
			try {
				Item item = itemFactory.createItem();
				System.out.println(item);
				items.add(item);
			} catch (InvalidItemSizeException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void run() {
		appRunning = true;
		System.out.println("\n\uD83D\uDD0B Starting machine: " + rvm.getRvmId());
		rvm.startMachine();
		mainLoop();
	}

	public void mainLoop() {
		while (appRunning) {
			try {
				if (rvm.IsMachineFull()) {
					handleFullMachine(rvm.getFullPile());
				}
				if (rvm.machineIsUsable()) {
					UserInterface.displayMenu();
					handleMainMenuActions();
				}
			} catch (Exception e) {
				handleAppException(e);
			} finally {
				inactivityTimer.resetTimer();
			}
		}
	}

	private void handleMainMenuActions() {
		int userInput = getUserAction();
		// Gotta put these actions to their own methods later 😊
		switch (userInput) {
			case 1:
				if (rvm.wrinkledItemDetected(items.getFirst())) {
					handleWrinkledItem(items.getFirst());
					break;
				}
				Item itemToRecycle = items.getFirst();
				System.out.println("Recycling item: " + itemToRecycle);
				boolean successfullyRecycled = rvm.recycleItem(itemToRecycle);
				if (!successfullyRecycled) {
					break;
				}
				short itemsLen = (short) items.size();
				short recyclablesLeft = (short) (itemsLen - rvm.recyclingSessionRecycledAmount);
				UserInterface.displayRecyclingInfo(
						rvm.recyclingSessionTotalValue,
						recyclablesLeft,
						rvm.recyclingSessionRecycledAmount
				);
				items.remove(itemToRecycle);
				break;
			case 2:
				rvm.printReceipt();
				break;
			case 3:
				if (rvm.recyclingSessionTotalValue == null) {
					System.out.println("Nothing to donate!\nPlease recycle something in order to donate.");
					return;
				}
				UserInterface.displayCharitySelectionMenu();
				break;
			case 4:
				System.exit(0);
				break;
			default:
				throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleFullMachine(String pile) {
		inactivityTimer.resetTimer();
		UserInterface.displayMachineError(pile + " Limit Reached");
		if (rvm.recyclingSessionRecycledAmount > 0) {
			// Print receipt if in middle of recycling the machine is full.
			rvm.printReceipt();
		}
		UserInterface.displayExceptionMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1:
				System.out.println("Fixing....");
				break;
			case 2:
				System.exit(0);
			default:
				throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleWrinkledItem(Item item) {
		inactivityTimer.resetTimer();
		UserInterface.displayMachineError("Can't insert an wrinkled item.");
		UserInterface.displayWrinkledItemMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1:
				item.setItemStatus(ItemStatus.UNWRINKLED);
				break;
			case 2:
				System.out.println("Resuming...");
				break;
			default:
				throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleAppException(Exception e) {
		if (rvm.isValidSleepModeException(e)) {
			rvm.exitFromSleepMode();
			return;
		}
		System.out.println("Error notified: " + e.getMessage());
		System.out.println("Please try again.");
	}
}
