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

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.DoubleStream;

/**
 * Represents a plastic bottle item that can be inserted to the RVM.
 * @author evnct
 * @since 1.0.0
 */
public class PlasticBottle extends RecyclableItem implements ItemCreation {
    private final double[] standardSizes = {0.33, 0.5, 0.95, 1.5, 2.0};
    private final double[] sizeComparisonValues = {0.35, 1.0};
    private final double[] redemptionValue = {0.10, 0.20, 0.40};

    private final double chosenSize;
    private double determinedValue;

    public PlasticBottle() throws InvalidItemSizeException {
        super(ItemType.BOTTLE, ItemMaterial.PLASTIC, 0.0);
        chosenSize = selectRandomSize(standardSizes);

        if (DoubleStream.of(standardSizes).noneMatch(s -> s == chosenSize)) {
            throw new InvalidItemSizeException("Current size is not in standardSizes array.");
        }

        determineItemValue();
    }

    @Override
    public void determineItemValue() {
        boolean small = chosenSize <= sizeComparisonValues[0];
        boolean medium = (chosenSize > sizeComparisonValues[0]) && (chosenSize <= sizeComparisonValues[1]);
        boolean large = chosenSize > sizeComparisonValues[1];

        if (small) {
            determinedValue = redemptionValue[0];
        } else if (medium) {
            determinedValue = redemptionValue[1];
        } else if (large) {
            determinedValue = redemptionValue[2];
        }
    }

    @Override
    public String toString() {
        return "PlasticBottle{" +
                "standardSizes=" + Arrays.toString(standardSizes) +
                ", sizeComparisonValues=" + Arrays.toString(sizeComparisonValues) +
                ", redemptionValue=" + Arrays.toString(redemptionValue) +
                ", chosenSize=" + chosenSize +
                ", value=" + determinedValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlasticBottle that = (PlasticBottle) o;
        return Double.compare(chosenSize, that.chosenSize) == 0
                && Double.compare(determinedValue, that.determinedValue) == 0
                && Objects.deepEquals(standardSizes, that.standardSizes)
                && Objects.deepEquals(sizeComparisonValues, that.sizeComparisonValues)
                && Objects.deepEquals(redemptionValue, that.redemptionValue
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(standardSizes),
                Arrays.hashCode(sizeComparisonValues),
                Arrays.hashCode(redemptionValue), chosenSize, determinedValue
        );
    }
}
