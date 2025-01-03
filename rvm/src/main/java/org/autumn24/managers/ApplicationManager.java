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

package org.autumn24.managers;

import org.autumn24.UserInterface;
import org.autumn24.authentication.AuthenticatedUser;
import org.autumn24.authentication.Authentication;
import org.autumn24.enviromental_impact.EcoStatSelector;
import org.autumn24.exceptions.InvalidItemSizeException;
import org.autumn24.exceptions.InvalidOptionException;
import org.autumn24.items.Item;
import org.autumn24.items.ItemFactory;
import org.autumn24.items.ItemStatus;
import org.autumn24.rvm.InactivityTimer;
import org.autumn24.rvm.ReverseVendingMachine;
import org.autumn24.rvm.enums.ReverseVendingMachineStatus;
import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A class that manages main actions in the application.
 */
public class ApplicationManager {

	/**
	 * Scanner to process user inputs.
	 */
	public static final Scanner scanner = new Scanner(System.in);
	/**
	 * List containing items to recycle.
	 */
	private static final ArrayList<Item> items = new ArrayList<>();
	/**
	 * Reverse vending machine to be managed.
	 */
	private static ReverseVendingMachine rvm;
	/**
	 * Manager that manages all the stored application data.
	 */
	private static AppDataManager appDataManager;
	/**
	 * Manager that manages the authentication and authorization of the app.
	 */
	private static AuthManager authManager;
	/**
	 * Boolean value representing is the app running or not.
	 */
	public boolean appRunning = false;
	/**
	 * Inactivity timer to handle idle (sleep)-mode.
	 */
	private InactivityTimer inactivityTimer;
	/**
	 * User using the reverse vending machine.
	 */
	private User user;

	/**
	 * Creates a new application manager with default values.
	 */
	public ApplicationManager() {
		appDataManager = new AppDataManager("appData.json");
		authManager = new AuthManager(appDataManager);
		generateBottles(new ItemFactory());
	}

	/**
	 * Gets user action input to start app actions.
	 *
	 * @return A number representing the user choice from the menu.
	 */
	private static int getUserAction() {
		if (!scanner.hasNextLine()) {
			scanner.nextLine();
			return 0;
		}
		String choice = scanner.nextLine();
		/* If RVM has gone to sleep mode while waiting on user action,
		return invalid option to activate sleep-mode recovery. */
		return ReverseVendingMachineStatus.IDLE.equals(rvm.getRvmStatus()) ? 0 : Integer.parseInt(choice);
	}

	/**
	 * Does the necessary configurations and starts the main loop of the application.
	 */
	public void run() {
		appRunning = true;
		appDataManager.loadJsonAppData();
		rvm = appDataManager.getAppData().getRvm();
		rvm.startMachine();
		user = AuthManager.getUserById("Guest");
		inactivityTimer = new InactivityTimer(rvm);
		inactivityTimer.resetTimer();
		mainLoop();
	}

	/**
	 * The main loop of the application.
	 */
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

	/**
	 * Checks current machine status and executes corresponding handler.
	 */
	private void machineStateToHandler() {
		if (authManager.isLoggedInAsEmployee()) {
			handleAdminMenuActions();
		} else if (rvm.IsMachineFull()) {
			handleFullMachine();
		} else if (rvm.machineIsUsable()) {
			handleMainMenuActions();
		}
	}

	/**
	 * Generates x amount of bottles to recycle.
	 *
	 * @param itemFactory Factory used to create recyclables.
	 */
	private void generateBottles(ItemFactory itemFactory) {
		System.out.println("\nGenerated bottles: ");
		final byte ITEMS_TO_GENERATE_LIMITED = 8; // SIMULATION LIMITATION FOR DEMO
		for (int i = 0; i < ITEMS_TO_GENERATE_LIMITED; i++) {
			try {
				Item item = itemFactory.createItem();
				System.out.println(item);
				items.add(item);
			} catch (InvalidItemSizeException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Executes the main menu actions from user choice.
	 */
	private void handleMainMenuActions() {
		authenticatedUserToMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1 -> handleInsert();
			case 2 -> handleReceipt();
			case 3 -> handleDonation();
			case 4 -> handleUserAuth();
			case 5 -> appRunning = false;
			case 6 -> handleEcoStats(user);
			default -> throw new InvalidOptionException();
		}
	}

	/**
	 * Handles registered users eco stats.
	 * Displays how many bottles recycled, total value, total energy saved.
	 *
	 * @param user The registered user to handle the eco stats for.
	 */
	private void handleEcoStats(User user) {
		if (authManager.isLoggedInAsRecycler()) {
			UserInterface.showEcoStats((RegisteredRecycler) user);
			// For now these can be here
			BigDecimal energySaved = EcoStatSelector.energySaved(getUserAction(), user);
			BigDecimal roundedResult = energySaved.setScale(4, RoundingMode.CEILING);
			System.out.printf("\nYOU HAVE SAVED %s kWh OF ENERGY!", String.valueOf(roundedResult).toUpperCase());
		} else {
			System.out.println("You must be logged in to view eco stats");
		}
	}

	/**
	 * Handles admin menu actions from user choice.
	 */
	private void handleAdminMenuActions() {
		authenticatedUserToMenu();
		int userInput = getUserAction();
		if (rvm.IsMachineFull()) {
			switch (userInput) {
				case 1 -> handleRvmEmptying();
				case 2 -> {
					user = AuthManager.getUserById("Guest");
					AuthManager.setAuthenticatedUser(AuthenticatedUser.GUEST);
				}
				default -> throw new InvalidOptionException();
			}
		} else {
			if (userInput == 1) {
				System.out.println("Logging out...");
				user = AuthManager.getUserById("Guest");
				AuthManager.setAuthenticatedUser(AuthenticatedUser.GUEST);
				return;
			}
			throw new InvalidOptionException();
		}
	}

	/**
	 * Handles users insert action.
	 */
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

	/**
	 * Handles users receipt action.
	 */
	private void handleReceipt() {
		if (notValidSessionTotal()) {
			System.out.println("Recycle something to print a receipt!");
			return;
		}
		updateAllUserAppData();
		rvm.printReceipt();
		rvm.resetSessionCounters();
	}

	/**
	 * Handles users donation action.
	 */
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
			default -> throw new InvalidOptionException();
		}
		updateAllUserAppData();
		rvm.resetSessionCounters();
	}

	/**
	 * Checks whether a session total is valid, and not, for example null.
	 *
	 * @return Boolean value representing is the total value valid.
	 */
	private boolean notValidSessionTotal() {
		return rvm.recyclingSession.getRecyclingSessionTotalValue().equals(BigDecimal.ZERO);
	}

	/**
	 * Handles user authentication action.
	 */
	private void handleUserAuth() {
		inactivityTimer.resetTimer();
		System.out.print("Enter user id: ");
		String userId = scanner.nextLine();
		while (userId.isEmpty()) {
			System.out.println("Try again...");
			System.out.print("=> ");
			userId = scanner.nextLine();
		}
		boolean validUser = Authentication.userExists(userId);
		if (!validUser) {
			System.out.println("User authentication failed...");
			return;
		}
		Authentication.authenticateUser(userId);
		user = AuthManager.getUserById(userId);
		System.out.println("User " + Objects.requireNonNull(user).getUserName() + " authenticated successfully.");
		System.out.println("Role: " + user.getUserRole());
	}

	/**
	 * Handles a situation where at least one of machines piles are full.
	 */
	private void handleFullMachine() {
		inactivityTimer.resetTimer();
		UserInterface.displayMachineError("Machine Limit Reached");
		if (rvm.recyclingSession.getRecyclingSessionRecycledAmount() > 0) {
			updateAllUserAppData();
			rvm.printReceipt();
			rvm.resetSessionCounters();
		}
		UserInterface.displayExceptionMenu();
		int userInput = getUserAction();
		switch (userInput) {
			case 1 -> handleUserAuth();
			case 2 -> appRunning = false;
		}
	}

	/**
	 * Handles emptying the machines piles when they are full.
	 */
	private void handleRvmEmptying() {
		if (rvm.IsMachineFull()) {
			System.out.println("Emptying all piles...");
			rvm.recyclables.values().forEach(recyclableData -> recyclableData.setRecyclingLimitCounter((short) 0));
			rvm.setRvmStatus(null);
			appDataManager.updateAppDataToJson();
			System.out.println("All piles cleared!");
		} else {
			System.out.println("Machine is already empty!");
		}
	}

	/**
	 * Handles a situation where users inserted item is wrinkled and therefore invalid.
	 *
	 * @param item The item that was inserted.
	 */
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
				throw new InvalidOptionException();
		}
	}

	/**
	 * Handles the app main loop exceptions.
	 *
	 * @param e The exception to handle.
	 */
	private void handleAppException(Exception e) {
		if (rvm.isValidSleepModeException(e)) {
			rvm.exitFromSleepMode();
			return;
		}
		System.out.println("Error notified: " + e.getMessage());
		System.out.println("Please try again.");
	}

	/**
	 * Displays correct menu for authenticated user.
	 */
	private void authenticatedUserToMenu() {
		if (authManager.isLoggedInAsRecycler()) {
			UserInterface.displayLoggedInRecyclerMenu(
					user.getUserName(),
					rvm.recyclingSession.getRecyclingSessionTotalValue(),
					(short) items.size(),
					rvm.recyclingSession.getRecyclingSessionRecycledAmount());
		} else if (authManager.isLoggedInAsEmployee()) {
			UserInterface.displayAdminMenu(rvm.IsMachineFull());
		} else {
			UserInterface.displayMenu(
					rvm.recyclingSession.getRecyclingSessionTotalValue(),
					(short) items.size(),
					rvm.recyclingSession.getRecyclingSessionRecycledAmount()
			);
		}
	}

	/**
	 * Updates all the user app data to database (JSON).
	 * Used when session is ending to store session specific data for users.
	 */
	private void updateAllUserAppData() {
		// Need to rework on this 😩 - but works for now
		/* THIS IS ART NO JUDGMENT PLS */
		if (authManager.isLoggedInAsRecycler()) {
			RegisteredRecycler recycler = (RegisteredRecycler) user;

			// Updates total plastic bottles recycled
			long totalPlasticBottlesRecycled = recycler.getTotalPlasticBottlesRecycled();
			long newTotalPlasticBottlesRecycled = totalPlasticBottlesRecycled + rvm.recyclingSession.getRecyclingSessionRecycledPlasticBottles();
			recycler.setTotalPlasticBottlesRecycled(newTotalPlasticBottlesRecycled);

			// Updates total glass bottles recycled
			long totalGlassBottlesRecycled = recycler.getTotalGlassBottlesRecycled();
			long newTotalGlassBottlesRecycled = totalGlassBottlesRecycled + rvm.recyclingSession.getRecyclingSessionRecycledGlassBottles();
			recycler.setTotalGlassBottlesRecycled(newTotalGlassBottlesRecycled);

			// Updates total aluminium cans recycled
			long totalAluminiumCansRecycled = recycler.getTotalAluminiumCansRecycled();
			long newTotalAluminiumCansRecycled = totalAluminiumCansRecycled + rvm.recyclingSession.getRecyclingSessionRecycledAluminumBottles();
			recycler.setTotalAluminiumCansRecycled(newTotalAluminiumCansRecycled);

			// Update total items recycled
			((RegisteredRecycler) user).setTotalItemsRecycled();

			// Updated total values recycled
			BigDecimal totalValueRecycled = recycler.getRedeemedTotalValue();
			BigDecimal newTotalValueRecycled = totalValueRecycled.add(rvm.recyclingSession.getRecyclingSessionTotalValue());
			recycler.setRedeemedTotalValue(newTotalValueRecycled);
		}
		appDataManager.updateAppDataToJson();
	}
}
