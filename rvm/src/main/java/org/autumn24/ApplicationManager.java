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
import org.autumn24.rvm.InactivityTimer;
import org.autumn24.rvm.ReverseVendingMachine;
import org.autumn24.rvm.Status;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that manages main actions in the application.
 * @author miikaran
 * @since 1.0.0
 */
public class ApplicationManager {

    public static final Scanner scanner = new Scanner(System.in);
    private final ReverseVendingMachine rvm;
    private final InactivityTimer inactivityTimer;
    private ArrayList<Item> items = new ArrayList<>();

    public ApplicationManager(ReverseVendingMachine rvm){
        this.rvm = rvm;
        this.inactivityTimer = new InactivityTimer(rvm);
        // Generate random bottles at init
        generateBottles(new ItemFactory());
    }

    public void run(){
        if(!rvm.getRvmStatus().equals(Status.OPERATIONAL)){
            // Tähän ehkä myöhemmin sellane et jos ei oo toiminnassa ni täytyy employeena eli
            // vähän niiku adminina käydä korjaamassa se vika siitä koneesta.
            System.out.println("Machine '" + rvm.getRvmId() + "' is not operational :(");
            return;
        }
        System.out.println("\nStarting Machine '" + rvm.getRvmId() + "'...");
        rvm.setRvmStatus(Status.INUSE);
        while (true){
            UserInterface.displayMenu();
            handleActions();
            inactivityTimer.resetTimer();
        }
    }

    private void handleActions(){
        try{
            System.out.print("=> ");
            int userInput = getUserAction();
            switch (userInput){
                case 1 -> rvm.recycleItem(items.getFirst());
                case 2 -> System.out.println("Unwrinkling");
                case 3 -> rvm.printReceipt();
                case 4 -> System.out.println("Donating");
                case 5 -> System.exit(0);
                default -> throw new Exception("Invalid option");
            }
        } catch(Exception e){
            if(rvm.getRvmStatus().equals(Status.IDLE)){
                System.out.println("\n\uD83D\uDD0B Powering up machine: " + rvm.getRvmId());
                return;
            }
            System.out.println(e.getMessage());
        }
    }

    private int getUserAction() throws Exception {
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
