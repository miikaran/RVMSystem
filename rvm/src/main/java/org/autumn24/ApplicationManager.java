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
import org.autumn24.rvm.ReverseVendingMachine;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that manages main actions in the application.
 * @author miikaran
 * @since 1.0.0
 */
public class ApplicationManager {

    private static final Scanner scanner = new Scanner(System.in);
    private final ReverseVendingMachine rvm;
    private final UserInterface rvmUI;
    private ArrayList<Item> items = new ArrayList<>();

    public ApplicationManager(ReverseVendingMachine rvm, UserInterface rvmUI){
        this.rvm = rvm;
        this.rvmUI = rvmUI;
        // Generate random bottles at init
        generateBottles(new ItemFactory());
    }

    public void run(){
        while (true){
            rvmUI.displayMenu();
            handleActions();
        }
    }

    private void handleActions(){
        try{
            System.out.print("=> ");
            int userInput = getUserChoice();
            switch (userInput){
                case 1 -> rvm.recycleItem(items.getFirst());
                case 2 -> System.out.println("Unwrinkling");
                case 3 -> rvm.printReceipt();
                case 4 -> System.out.println("Donating");
                case 5 -> System.exit(0);
                default -> throw new Exception("Invalid option...");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private int getUserChoice() throws Exception {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();  // Clear invalid input
            throw new Exception("Invalid input, expected an integer.");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer for new line
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
}
