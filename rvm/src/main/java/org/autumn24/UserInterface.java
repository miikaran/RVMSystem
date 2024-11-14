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

/**
 * A class that is used to display ui views.
 * @author miikaran
 * @since 1.0.0
 */
public class UserInterface {

    public static void displayMenu(){
        System.out.println();
        System.out.println("1 ------> INSERT");
        System.out.println("2 ------> UNWRINKLE");
        System.out.println("3 ------> RECEIPT");
        System.out.println("4 ------> DONATE");
        System.out.println("5 ------> EXIT");
        System.out.println();
    }

    // Possible UI views for later 😊
    public static void displayStats() {} // 📊
    public static void displayEcoTip(){} // ♻️
    public static void displayBottleAnimation(){} //😲


}
