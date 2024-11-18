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

import org.autumn24.exceptions.InvalidItemSizeException;
import org.autumn24.items.Item;
import org.autumn24.items.ItemFactory;
import org.autumn24.items.ItemStatus;
import org.autumn24.rvm.InactivityTimer;
import org.autumn24.rvm.ReverseVendingMachine;
import org.autumn24.rvm.enums.ReverseVendingMachineStatus;
import org.autumn24.users.Employee;
import org.autumn24.users.GuestRecycler;
import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class that manages main actions in the application.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class ApplicationManager {

	public static final Scanner scanner = new Scanner(System.in);
	public static final ReverseVendingMachine rvm = new ReverseVendingMachine();
	private final InactivityTimer inactivityTimer;
	private final ArrayList<Item> items = new ArrayList<>();
	public boolean appRunning = false;
	private User user = new GuestRecycler();

	public ApplicationManager() {
		this.inactivityTimer = new InactivityTimer(rvm);
		generateBottles(new ItemFactory());
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
            return invalid option to activate sleep-mode recovery.
            */
			return 0;
		}

		return choice;
	}

	public void run() {
		appRunning = true;
		System.out.println("\n\uD83D\uDD0B Starting machine: " + rvm.getRvmId());
		rvm.startMachine();
		inactivityTimer.resetTimer();
		mainLoop();
	}

	private void mainLoop() {
		while (appRunning) {
			try {
				machineStateToHandler();
			} catch (Exception e) {
				handleAppException(e);
			} finally {
				inactivityTimer.resetTimer();
			}
		}
	}

	private void machineStateToHandler() {
		if (rvm.machineIsUsable()) {
			handleMainMenuActions();
		} else if (rvm.IsMachineFull()) {
			handleFullMachine(rvm.getFullPile());
		}
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

	private void handleMainMenuActions() {
		UserInterface.displayMenu(
				rvm.recyclingSessionTotalValue,
				(short) items.size(),
				rvm.recyclingSessionRecycledAmount
		);
		int userInput = getUserAction();
		switch (userInput) {
			case 1 -> handleInsert();
			case 2 -> handleReceipt();
			case 3 -> handleDonation();
			case 4 -> handleUserAuth();
			case 5 -> appRunning = false;
			default -> throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleInsert() {
		if (items.isEmpty()) {
			System.out.println("You have recycle all of our bottles!");
			System.out.println("Choose receipt or donation");
			return;
		}
		Item itemToRecycle = items.getFirst();
		System.out.println("Inserting item: " + itemToRecycle);
		if (rvm.wrinkledItemDetected(itemToRecycle)) {
			handleWrinkledItem(itemToRecycle);
		}
		boolean successfullyRecycled = rvm.recycleItem(itemToRecycle);
		if (!successfullyRecycled) {
			return;
		}
		items.remove(itemToRecycle);
	}

	private void handleReceipt() {
		if (rvm.recyclingSessionTotalValue == null) {
			System.out.println("Recycle something to print a receipt!");
			return;
		}
		rvm.printReceipt();
	}

	private void handleDonation() {
		inactivityTimer.resetTimer();
		if (rvm.recyclingSessionTotalValue == null) {
			System.out.println("Nothing to donate!\nPlease recycle something in order to donate.");
			return;
		}
		UserInterface.displayCharitySelectionMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1, 2, 3 -> rvm.donateToCharity(userInput);
			default -> throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleUserAuth() {
		inactivityTimer.resetTimer();
		System.out.print("Enter user id: ");
		String userId = scanner.nextLine();
		while (userId.isEmpty()) {
			System.out.println("Try again...");
			System.out.print("=> ");
			userId = scanner.nextLine();
		}
		HashMap<String, Object> authData = new HashMap<>(); // UserManager.isAuthenticated(userId)
		authData.put("success", true);
		authData.put("role", "recycler");
		boolean success = (boolean) authData.get("success");
		String role = (String) authData.get("role");
		if (!success) {
			System.out.println("User authentication failed :(");
			return;
		}
		if (role.isEmpty()) {
			System.out.println("Role not found for user id: " + userId);
			return;
		}
		switch (role) {
			case "guest":
				// user = gson.fromJson(reader, Employee.class)
				user = new GuestRecycler();
				System.out.println("Guest authenticated successfully!");
				break;
			case "recycler":
				// user = gson.fromJson(reader, RegisteredRecycler.class)
				user = new RegisteredRecycler("miihael", userId, "recycler", (short) 123, (short) 123);
				System.out.println("Recycler authenticated successfully!");
				break;
			case "admin":
				// user = gson.fromJson(reader, Employee.class)
				user = new Employee(userId, true, "Juuso", "Jepulis");
				System.out.println("Admin authenticated successfully!");
				break;
			default:
				throw new IllegalArgumentException("Invalid role: " + role);
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
				appRunning = false;
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
