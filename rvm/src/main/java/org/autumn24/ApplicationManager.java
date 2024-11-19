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
import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.math.BigDecimal;
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
	private static final ArrayList<Item> items = new ArrayList<>();
	private static ReverseVendingMachine rvm;
	private static AppDataManager appDataManager;
	private static AuthManager authManager;
	public boolean appRunning = false;
	private InactivityTimer inactivityTimer;
	private User user;

	public ApplicationManager() {
		appDataManager = new AppDataManager("appData.json");
		authManager = new AuthManager(appDataManager);
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
		appDataManager.loadJsonAppData();
		rvm = appDataManager.appData.getRvm();
		rvm.startMachine();
		user = authManager.getUserById("Guest");
		inactivityTimer = new InactivityTimer(rvm);
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
		if (authManager.isLoggedInAsEmployee()) {
			handleAdminMenuActions();
		} else if (rvm.machineIsUsable()) {
			handleMainMenuActions();
		} else if (rvm.IsMachineFull()) {
			handleFullMachine();
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
		authStatusToMenu();
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

	private void handleAdminMenuActions() {
		authStatusToMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1 -> handleRvmEmptying();
			case 2 -> {
				user = authManager.getUserById("Guest");
				authManager.setAuthStatus(AuthStatus.GUEST);
			}
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
			return;
		}
		boolean successfullyRecycled = rvm.recycleItem(itemToRecycle);
		if (!successfullyRecycled) {
			return;
		}
		items.remove(itemToRecycle);
	}

	private void handleReceipt() {
		if (notValidSessionTotal()) {
			System.out.println("Recycle something to print a receipt!");
			return;
		}
		rvm.printReceipt();
		totalValueResetAfterProcessing();
		updateAppData();
	}

	private void handleDonation() {
		if (notValidSessionTotal()) {
			System.out.println("Nothing to donate!\nPlease recycle something in order to donate.");
			return;
		}
		inactivityTimer.resetTimer();
		UserInterface.displayCharitySelectionMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1, 2, 3 -> rvm.donateToCharity(userInput);
			default -> throw new IllegalArgumentException("Invalid option...");
		}
		totalValueResetAfterProcessing();
		updateAppData();
	}

	private boolean notValidSessionTotal() {
		return rvm.getRecyclingSessionTotalValue().equals(BigDecimal.ZERO);
	}

	private void totalValueResetAfterProcessing() {
		rvm.setRecyclingSessionTotalValue(BigDecimal.ZERO);
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
		boolean userAuthenticated = authManager.authenticateUser(userId);
		if (userAuthenticated) {
			user = authManager.getUserById(userId);
			System.out.println("User " + user.getUserName() + " authenticated successfully.");
			return;
		}
		System.out.println("User authentication failed...");
	}

	private void handleFullMachine() {
		inactivityTimer.resetTimer();
		UserInterface.displayMachineError("Machine Limit Reached");
		if (rvm.recyclingSessionRecycledAmount > 0) {
			// Print receipt if in middle of recycling the machine is full.
			rvm.printReceipt();
		}
		UserInterface.displayExceptionMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1 -> handleUserAuth();
			case 2 -> appRunning = false;
			default -> throw new IllegalArgumentException("Invalid option...");
		}
	}

	private void handleRvmEmptying() {
		if (rvm.IsMachineFull()) {
			System.out.println("Emptying all piles...");
			rvm.plasticBottleLimitCounter = 0;
			rvm.glassBottleLimitCounter = 0;
			rvm.aluminiumCanLimitCounter = 0;
			rvm.rvmStatus = null;
			appDataManager.updateAppDataToJson();
			System.out.println("All piles cleared!");
		} else {
			System.out.println("Machine is already empty!");
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

	private void authStatusToMenu() {
		if (authManager.isLoggedInAsRecycler()) {
			UserInterface.displayLoggedInRecyclerMenu(
					user.getUserName(),
					rvm.getRecyclingSessionTotalValue(),
					(short) items.size(),
					rvm.recyclingSessionRecycledAmount);
		} else if (authManager.isLoggedInAsEmployee()) {
			UserInterface.displayAdminMenu();
		} else {
			UserInterface.displayMenu(
					rvm.getRecyclingSessionTotalValue(),
					(short) items.size(),
					rvm.recyclingSessionRecycledAmount
			);
		}
	}

	private void updateAppData() {
		// Need to rework on this 😩 - but works for now
		if (authManager.isLoggedInAsRecycler() && user instanceof RegisteredRecycler) {
			int totalBottlesRecycled = ((RegisteredRecycler) user).getTotalBottlesRecycled();
			int newTotalBottlesRecycled = totalBottlesRecycled + rvm.recyclingSessionRecycledAmount;
			((RegisteredRecycler) user).setTotalBottlesRecycled((short) newTotalBottlesRecycled);
			BigDecimal totalValueRecycled = ((RegisteredRecycler) user).getRedeemedTotalValue();
			BigDecimal newTotalValueRecycled = totalValueRecycled.add(rvm.getRecyclingSessionTotalValue());
			((RegisteredRecycler) user).setRedeemedTotalValue(newTotalValueRecycled);
		}
		appDataManager.updateAppDataToJson();
	}
}
