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

import org.autumn24.excpetion.InvalidItemMaterialException;
import org.autumn24.excpetion.MissingItemMaterialException;

import java.util.Scanner;
import java.util.UUID;

/**
 * A class that represents a reverse vending machine.
 * @author miikaran
 * @since 1.0.0
 */
public class ReverseVendingMachine {

    public short numberOfAluminiumCansRecycled;
    public short numberOfGlassBottlesRecycled;
    public short numberOfPlasticBottlesRecycled;
    public double recyclingSessionTotalValue;
    public String rvmId;

    public static final Scanner scanner = new Scanner(System.in);

    public ReverseVendingMachine(){
        rvmId = UUID.randomUUID().toString();
        displayRvmMenu();
    }

    public void displayRvmMenu(){
        System.out.println("1 ------> INSERT");
        System.out.println("2 ------> UNWRINKLE");
        System.out.println("3 ------> RECEIPT");
        System.out.println("4 ------> DONATE");
        System.out.println("5 ------> EXIT");
        while(true){
            try{
                System.out.print("=> ");
                if (!scanner.hasNextInt()) {
                    scanner.nextLine(); // Clear invalid input
                    throw new Exception("Invalid input, expected an integer.");
                }
                int userInput = scanner.nextInt();
                scanner.nextLine(); // Clear input buffer for new line
                switch (userInput){
                    case 1 -> System.out.println("Inserting");
                    case 2 -> System.out.println("Unwrinkling");
                    case 3 -> System.out.println("Receipting");
                    case 4 -> System.out.println("Donating");
                    case 5 -> System.exit(0);
                    default -> throw new Exception("Invalid option...");
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
    }

    public RecyclingPile recycleItem(/*Item item*/) {
        System.out.println("Recycling item...'" + /*item +*/ "'");
        String material = "hellurei"; //item.getItemMaterial();
        double value = 50.0; //item.getValue();
        if (material == null || material.isEmpty()) {
            throw new MissingItemMaterialException("Material '" + material + "' is either null or empty.");
        }
        // Check if provided item's material can be matched to a valid enum.
        RecyclingPile pile;
        try { pile = RecyclingPile.valueOf(material.toUpperCase()); }
        catch (IllegalArgumentException e){
            throw new InvalidItemMaterialException("Material'" + material + "' not found in RecyclingPile.", e);
        }
        increaseRecycledItemsCounter(pile);
        recyclingSessionTotalValue += value;
        return pile;
    }

    public void increaseRecycledItemsCounter(RecyclingPile pile){
        switch (pile) {
            // Increase number of type X recycled items.
            case METAL -> numberOfAluminiumCansRecycled++;
            case GLASS -> numberOfGlassBottlesRecycled++;
            case PLASTIC -> numberOfPlasticBottlesRecycled++;
        }
    }

    public Receipt printReceipt(){
        System.out.println("Printing receipt...");
        Receipt receipt = new Receipt(
                numberOfAluminiumCansRecycled,
                numberOfGlassBottlesRecycled,
                numberOfPlasticBottlesRecycled,
                recyclingSessionTotalValue
        );
        // Display receipt to terminal
        receipt.displayReceipt();
        return receipt;
    }
}
