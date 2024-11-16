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

import org.autumn24.excpetion.InvalidItemSizeException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an aluminium can that an RVM can accept.
 *
 * @author evnct
 * @since 1.0.0
 */
class AluminiumCan extends RecyclableItem implements Item {
    private final double[] standardSizes = {0.25, 0.33, 0.5};
    private final double VALUE_FOR_ALL_CANS = 0.15;
    private final double chosenSize = selectRandomSize(standardSizes);

    private BigDecimal determinedValue;

    public AluminiumCan() throws InvalidItemSizeException {
        super(ItemType.CAN, ItemMaterial.ALUMINIUM, 0.0);
        initializeItem(chosenSize, standardSizes);
    }

    @Override
    public BigDecimal getDeterminedValue() {
        return determinedValue;
    }

    @Override
    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    @Override
    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * All aluminium cans are valued as 0.15 €
     * No matter the size.
     */
    @Override
    public void determineItemValue() {
        determinedValue = BigDecimal.valueOf(VALUE_FOR_ALL_CANS);
    }

    @Override
    public String toString() {
        return "AluminiumCan{" +
                "standardSizes=" + Arrays.toString(standardSizes) +
                ", VALUE_FOR_ALL_CANS=" + VALUE_FOR_ALL_CANS +
                ", chosenSize=" + chosenSize +
                ", determinedValue=" + determinedValue +
                ", itemStatus=" + this.getItemStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AluminiumCan that = (AluminiumCan) o;
        return Double.compare(chosenSize, that.chosenSize) == 0
                && Objects.deepEquals(standardSizes, that.standardSizes)
                && Objects.equals(determinedValue, that.determinedValue)
                && Objects.equals(getItemStatus(), that.getItemStatus()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.hashCode(standardSizes),
                VALUE_FOR_ALL_CANS, chosenSize, determinedValue, getItemStatus()
        );
    }
}
