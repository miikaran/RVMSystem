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

    private final String rvmId;
    public short numberOfAluminiumCansRecycled;
    public short numberOfGlassBottlesRecycled;
    public short numberOfPlasticBottlesRecycled;
    public BigDecimal recyclingSessionTotalValue;
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
        switch (material) {
            // Check if provided item's material can be matched to a valid enum.
            case ALUMINIUM -> pile = RecyclingPile.METAL;
            case GLASS -> pile = RecyclingPile.GLASS;
            case PLASTIC -> pile = RecyclingPile.PLASTIC;
            default -> throw new InvalidItemMaterialException("Material'" + material + "' not found in RecyclingPile.");
        }
        increaseRecycledItemsCounter(pile);
        increaseSessionTotalValue(value);
    }

    public void increaseRecycledItemsCounter(RecyclingPile pile) {
        switch (pile) {
            // Increase number of type X recycled items.
            case METAL -> numberOfAluminiumCansRecycled++;
            case GLASS -> numberOfGlassBottlesRecycled++;
            case PLASTIC -> numberOfPlasticBottlesRecycled++;
        }
    }

    public void increaseSessionTotalValue(BigDecimal value) {
        if (recyclingSessionTotalValue == null) {
            recyclingSessionTotalValue = BigDecimal.ZERO;
        }
        recyclingSessionTotalValue = recyclingSessionTotalValue.add(value);
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
}
