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

import org.autumn24.charity.Charity;
import org.autumn24.charity.charities.AlzheimerAssociation;
import org.autumn24.charity.charities.AmnestyInternational;
import org.autumn24.charity.charities.Greenpeace;

import java.math.BigDecimal;

/**
 * A class that is used to display ui views.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class UserInterface {

	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	public static final String LIGHT_GRAY = "\u001B[90m";
	public static final String BOLD = "\u001B[1m";
	public static final String UNDERLINE = "\u001B[4m";
	public static final String RESET = "\u001B[0m";

	public static void displayMenu(BigDecimal totalValue, short recyclablesLeft, short recycledAmount) {
		if (totalValue == null) totalValue = BigDecimal.ZERO;
		System.out.println();
		System.out.println(BOLD + BLUE + "‖═════════MAIN MENU══════════‖" + RESET);
		System.out.printf(YELLOW + "💰 Total Value: " + WHITE + "%.2f€" + RESET + "\n", totalValue);
		System.out.println(GREEN + "♻️ Recycled: " + WHITE + recycledAmount + " items" + RESET);
		System.out.println(CYAN + "🔄 Recyclables Left: " + WHITE + recyclablesLeft + " items" + RESET);
		System.out.println();
		System.out.println(GREEN + "[1] ➤ INSERT" + RESET);
		System.out.println(YELLOW + "[2] ➤ RECEIPT" + RESET);
		System.out.println(BLUE + "[3] ➤ DONATE" + RESET);
		System.out.println(CYAN + "[4] ➤ SCAN USER ID" + RESET);
		System.out.println(RED + "[5] ➤ EXIT" + RESET);
		System.out.println("\n\n");
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void displayMachineError(String message) {
		if (message == null || message.isEmpty()) {
			message = "Machine Error";
		}
		System.out.println();
		System.out.println(BOLD + RED + "ERROR DETECTED" + RESET);
		System.out.println(BOLD + YELLOW + "Error: " + WHITE + message + RESET);
	}

	public static void displayExceptionMenu() {
		System.out.println("\n\n");
		System.out.println(BOLD + RED + "⚠️ PLEASE CHOOSE AN ACTION ⚠️" + RESET);
		System.out.println(YELLOW + "[1] ➤ FIX AS EMPLOYEE" + RESET);
		System.out.println(WHITE + "[2] ➤ EXIT" + RESET);
		System.out.println("\n\n");
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void displayAdminMenu() {
		System.out.println("\n\n");
		System.out.println(BOLD + BLUE + "🔐 ADMIN MENU - SYSTEM MAINTENANCE 🔐" + RESET);
		System.out.println(GREEN + "[1] ➤ EMPTY MACHINE" + RESET + "    " + YELLOW + "Clear all recyclables." + RESET);
		System.out.println(WHITE + "[2] ➤ LOGOUT" + RESET + "          " + YELLOW + "Return to the main system." + RESET);
		System.out.println("\n\n");
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void displayWrinkledItemMenu() {
		System.out.println();
		System.out.println(BOLD + YELLOW + "🚨 WRINKLED ITEM DETECTED 🚨" + RESET);
		System.out.println(GREEN + "[1] ➤ UNWRINKLE ITEM" + RESET + "    Attempt to unwrinkle item.");
		System.out.println(WHITE + "[2] ➤ CONTINUE" + RESET + "          Try to continue");
		System.out.println();
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void displayCharitySelectionMenu() {
		System.out.println("\n\n");
		System.out.println(CYAN + BOLD + "🌟 Select a charity to donate to 🌟" + RESET);
		Charity[] charities = {
				Greenpeace.createGreenpeace(),
				AmnestyInternational.createAmnestyInternational(),
				AlzheimerAssociation.createAlzheimerAssociation()
		};
		for (int i = 0; i < charities.length; i++) {
			Charity charity = charities[i];
			int placement = i + 1;
			System.out.printf(BOLD + "%s ➤ %s" + RESET + YELLOW + " | %s%n" + RESET,
					placement, charity.name(), charity.cause());
		}
		System.out.println("\n\n");
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void displayLoggedInRecyclerMenu(String user, BigDecimal totalValue, short recyclablesLeft, short recycledAmount) {
		if (totalValue == null) totalValue = BigDecimal.ZERO;
		System.out.println("\n\n");
		System.out.printf(YELLOW + "💰 Total Value: " + WHITE + "%.2f€" + RESET + "\n", totalValue);
		System.out.println(GREEN + "♻️ Recycled: " + WHITE + recycledAmount + " items" + RESET);
		System.out.println(CYAN + "🔄 Recyclables Left: " + WHITE + recyclablesLeft + " items" + RESET);
		System.out.println();
		System.out.println(BOLD + BLUE + "======= LOGGED IN AS " + user.toUpperCase() + " =======" + RESET);
		System.out.println(GREEN + "[1] ➤ INSERT" + RESET);
		System.out.println(YELLOW + "[2] ➤ RECEIPT" + RESET);
		System.out.println(CYAN + "[3] ➤ DONATE" + RESET);
		System.out.println(RED + "[5] ➤ EXIT" + RESET);
		System.out.println(BOLD + BLUE + "======= SPECIAL OPTIONS =======" + RESET);
		System.out.println(PURPLE + "[6] ➤ STATS" + RESET);
		System.out.println(GREEN + "[7] ➤ AWARDS" + RESET);
		System.out.println("\n\n");
		System.out.print(BOLD + "=> " + RESET);
	}

	public static void clearScreen() {
        /*
        (tää toimii mul vaan jos suorittaa eri terminaalis tän ohjelman)
        System.out.println("\033[H\033[2J");
        System.out.flush();
        */
	}

	// Possible UI views for later 😊
	public static void displayStats() {
	} // 📊

	public static void displayEcoTip() {
	} // ♻️

	public static void displayBottleAnimation() {
	} //😲


}
