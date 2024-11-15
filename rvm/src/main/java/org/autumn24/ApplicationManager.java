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
import org.autumn24.rvm.*;

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
    private final ReverseVendingMachine rvm;
    private final InactivityTimer inactivityTimer;
    private final ArrayList<Item> items = new ArrayList<>();

    public ApplicationManager(ReverseVendingMachine rvm) {
        this.rvm = rvm;
        this.inactivityTimer = new InactivityTimer(rvm);
        // Generate random bottles at init
        generateBottles(new ItemFactory());
        inactivityTimer.resetTimer();
    }

    public void run() {
        if (!rvm.rvmStatus.equals(ReverseVendingMachineFunctionalStatus.OPERATIONAL)) {
            System.out.println("Machine: '" + rvm.getRvmId() + "' not operational.");
            return;
        }
        System.out.println("\n\uD83D\uDD0B Starting machine: " + rvm.getRvmId());
        rvm.rvmPwStatus = ReverseVendingMachinePowerStatus.ON;
        mainLoop();
    }

    public void mainLoop() {
        while (machineIsUsable()) {
            try {
                UserInterface.displayMenu();
                handleMainActions();
            } catch (Exception e) {
                if (isValidSleepModeException(e)) {
                    exitFromSleepMode();
                    continue;
                }
                System.out.println(e.getMessage());
            } finally {
                inactivityTimer.resetTimer();
            }
        }
    }

    private void handleMainActions() {
        System.out.print("=> ");
        if (!validateUserActionInputType()) {
            throw new IllegalArgumentException("Invalid input type, int expected.");
        }
        int userInput = getUserAction();
        if (rvm.rvmStatus.equals(ReverseVendingMachineStatus.IDLE)) {
            exitFromSleepMode();
            return;
        }
        switch (userInput) {
            case 1 -> rvm.recycleItem(items.getFirst());
            case 2 -> System.out.println("Unwrinkling");
            case 3 -> rvm.printReceipt();
            case 4 -> System.out.println("Donating");
            case 5 -> System.exit(0);
            default -> throw new IllegalArgumentException("Invalid option...");
        }
    }

    private void exitFromSleepMode() {
        if (rvm.rvmStatus.equals(ReverseVendingMachineStatus.IDLE)) {
            System.out.println("\n\uD83D\uDD0B Powering up machine: " + rvm.getRvmId());
            rvm.rvmStatus = ReverseVendingMachineStatus.IN_USE;
        }
    }

    private boolean validateUserActionInputType() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Clear invalid input
            return false;
        }
        return true;
    }

    private int getUserAction() throws IllegalArgumentException {
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

    private boolean isValidSleepModeException(Exception e) {
        return e instanceof IllegalArgumentException && rvm.rvmStatus.equals(ReverseVendingMachineStatus.IDLE);
    }

    private boolean machineIsUsable() {
        return (rvm.rvmFnStatus.equals(ReverseVendingMachineFunctionalStatus.OPERATIONAL)
                && rvm.rvmPwStatus.equals(ReverseVendingMachinePowerStatus.ON));
    }

}
