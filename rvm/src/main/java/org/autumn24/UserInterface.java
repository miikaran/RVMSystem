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

    public static void displayMenu(BigDecimal totalValue, short recyclablesLeft, short recycledAmount) {
        if (totalValue == null) totalValue = BigDecimal.ZERO;
        System.out.println();
        System.out.println(totalValue + "€ " + " Recycled: " + recycledAmount + " Recyclables left: " + recyclablesLeft);
        System.out.println("===================");
        System.out.println("1 ------> INSERT");
        System.out.println("2 ------> RECEIPT");
        System.out.println("3 ------> DONATE");
        System.out.println("4 ------> EXIT");
        System.out.println();
        System.out.print("=> ");
    }

    public static void displayMachineError(String message) {
        if (message == null || message.isEmpty()) {
            message = "This machine is not in use";
        }
        System.out.println();
        System.out.println("ERROR ⚠ ");
        System.out.println(message);
    }

    public static void displayExceptionMenu() {
        System.out.println();
        System.out.println("============ERROR MENU================");
        System.out.println("1 ------> FIX AS EMPLOYEE");
        System.out.println("2 ------> EXIT");
        System.out.println();
        System.out.print("=> ");
    }

    public static void displayWrinkledItemMenu() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("1 ------> UNWRINKLE ITEM");
        System.out.println("2 ------> CONTINUE");
        System.out.println();
        System.out.print("=> ");
    }

    public static void displayCharitySelectionMenu() {
        System.out.println();
        System.out.println("Select a charity you want donate to: ");
        Charity[] charities = {
                Greenpeace.createGreenpeace(),
                AmnestyInternational.createAmnestyInternational(),
                AlzheimerAssociation.createAlzheimerAssociation()
        };
        for (int i = 0; i < charities.length; i++) {
            Charity charity = charities[i];
            int placement = i + 1;
            System.out.printf("%s : %s | %s%n", placement, charity.name(), charity.cause());
        }
        System.out.println();
        System.out.print("=> ");
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
