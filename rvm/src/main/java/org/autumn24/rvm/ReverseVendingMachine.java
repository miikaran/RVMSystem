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

import org.autumn24.Recycle;
import org.autumn24.excpetion.InvalidItemMaterialException;
import org.autumn24.excpetion.MissingItemMaterialException;
import org.autumn24.items.Item;
import org.autumn24.items.ItemMaterial;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A class that represents a reverse vending machine.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class ReverseVendingMachine implements Recycle {

    public short ALUMINIUM_CANS_LIMIT = 3;
    public short GLASS_BOTTLES_LIMIT = 3;
    public short PLASTIC_BOTTLES_LIMIT = 3;

    private final String rvmId;
    public BigDecimal recyclingSessionTotalValue;

    // Keeps count of recycled items
    public short numberOfAluminiumCansRecycled;
    public short numberOfGlassBottlesRecycled;
    public short numberOfPlasticBottlesRecycled;

    // Keeps count of recyclable specific limits
    public short aluminiumCanLimitCounter;
    public short glassBottleLimitCounter;
    public short plasticBottleLimitCounter;

    public ReverseVendingMachineStatus rvmStatus;
    public ReverseVendingMachineFunctionalStatus rvmFnStatus;
    public ReverseVendingMachinePowerStatus rvmPwStatus;

    public ReverseVendingMachine() {
        rvmId = UUID.randomUUID().toString();
        rvmFnStatus = ReverseVendingMachineFunctionalStatus.OPERATIONAL;
        rvmPwStatus = ReverseVendingMachinePowerStatus.OFF;
    }

    public String getRvmId() {
        return rvmId;
    }

    @Override
    public void recycleItem(Item item) {
        System.out.println("Recycling item: " + item);
        ItemMaterial material = item.getItemMaterial();
        if (material == null) {
            throw new MissingItemMaterialException("Material is type null, ItemMaterial expected");
        }
        BigDecimal value = item.getDeterminedValue();
        RecyclingPile pile;
        boolean limitReached;
        switch (material) {
            case ALUMINIUM:
                limitReached = aluminiumLimitReached();
                pile = RecyclingPile.METAL;
                break;
            case GLASS:
                limitReached = glassBottleLimitReached();
                pile = RecyclingPile.GLASS;
                break;
            case PLASTIC:
                limitReached = plasticBottleLimitReached();
                pile = RecyclingPile.PLASTIC;
                break;
            default:
                throw new InvalidItemMaterialException("Material'" + material + "' not found in RecyclingPile.");
        }
        if(limitReached){
            System.out.println(pile.name() + " limit reached.");
            rvmStatus = ReverseVendingMachineStatus.FULL;
            return;
        }
        increaseRecycledItemsCounter(pile);
        increaseSessionTotalValue(value);
    }

    public void increaseRecycledItemsCounter(RecyclingPile pile) {
        switch (pile) {
            // Increase number of type X recycled item counters.
            case METAL: {
                numberOfAluminiumCansRecycled++;
                aluminiumCanLimitCounter++;
            }
            case GLASS: {
                numberOfGlassBottlesRecycled++;
                glassBottleLimitCounter++;
            }
            case PLASTIC: {
                numberOfPlasticBottlesRecycled++;
                plasticBottleLimitCounter++;
            }
        }
    }

    public void increaseSessionTotalValue(BigDecimal value) {
        if (recyclingSessionTotalValue == null) {
            recyclingSessionTotalValue = BigDecimal.ZERO;
        }
        recyclingSessionTotalValue = recyclingSessionTotalValue.add(value);
    }

    public void exitFromSleepMode() {
        if (rvmStatus.equals(ReverseVendingMachineStatus.IDLE)) {
            System.out.println("\n\uD83D\uDD0B Machine: " + rvmId + " recovering from sleep mode:");
            rvmStatus = ReverseVendingMachineStatus.IN_USE;
        }
    }

    public boolean isValidSleepModeException(Exception e) {
        // Used to identify exceptions that can be suppressed when recovering from sleepmode
        return e instanceof IllegalArgumentException && ReverseVendingMachineStatus.IDLE.equals(rvmStatus);
    }

    public boolean machineIsUsable() {
        return (rvmFnStatus.equals(ReverseVendingMachineFunctionalStatus.OPERATIONAL)
                && rvmPwStatus.equals(ReverseVendingMachinePowerStatus.ON)
                && !ReverseVendingMachineStatus.FULL.equals(rvmStatus));
    }

    public Receipt printReceipt() {
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


    public boolean aluminiumLimitReached(){
        return ALUMINIUM_CANS_LIMIT == aluminiumCanLimitCounter;
    }

    public boolean plasticBottleLimitReached(){
        return PLASTIC_BOTTLES_LIMIT == plasticBottleLimitCounter;
    }

    public boolean glassBottleLimitReached(){
        return GLASS_BOTTLES_LIMIT == glassBottleLimitCounter;
    }
}
