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

package org.autumn24.items;

import java.util.Random;

/**
 * Represents the base of all types of generated
 * items that are accepted by the rvm.
 * @author evnct
 * @since 1.0.0
 */
public abstract class RecyclableItem {
    // Used to build an item
    private final ItemType itemType;
    private final ItemMaterial itemMaterial;
    private final double itemSize;

    /* EMULATION LIMITATIONS */
    // So that the demo will not take eternity.
    private short maxItemsOfSingleType = 10;
    private short minItemsOfSingleType = 3;

    public RecyclableItem(ItemType itemType, ItemMaterial itemMaterial, double itemSize) {
        this.itemType = itemType;
        this.itemMaterial = itemMaterial;
        this.itemSize = itemSize;
    }

    // User could overwrite the default values
    // Note: Uncertain of the need for these set methods.

    public void setMaxItemsOfSingleType(short maxItemsOfSingleType) {
        this.maxItemsOfSingleType = maxItemsOfSingleType;
    }

    public void setMinItemsOfSingleType(short minItemsOfSingleType) {
        this.minItemsOfSingleType = minItemsOfSingleType;
    }

    public abstract double getSize();
    public abstract double getValue();

    /** Generates random number of items from a known range.
     * For example 3-10 plastic bottles are generated with each
     * having their own size.
     * @return int
     */
    public int generatedItemCount() {
        Random random = new Random();
        return random.nextInt(minItemsOfSingleType, maxItemsOfSingleType);
    }
}
