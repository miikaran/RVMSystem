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

/**
 * Represents the base of all types of generated
 * items that are accepted by the rvm.
 *
 * @author evnct
 * @since 1.0.0
 */
public abstract class RecyclableItem {

    /* USED TO BUILD AN ITEM */
    private final ItemType itemType;
    private final ItemMaterial itemMaterial;
    private final ItemStatus itemStatus;
    private final double itemSize;

    public RecyclableItem(ItemType itemType, ItemMaterial itemMaterial, double itemSize) {
        this.itemType = itemType;
        this.itemMaterial = itemMaterial;
        this.itemStatus = ItemStatus.selectRandomStatus();
        this.itemSize = itemSize;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public ItemMaterial getItemMaterial() {
        return itemMaterial;
    }

    @Override
    public String toString() {
        return "RecyclableItem{itemType=%s, itemMaterial=%s, itemSize=%s}".formatted(itemType, itemMaterial, itemSize);
    }
}
