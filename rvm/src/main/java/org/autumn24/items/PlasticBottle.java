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

import java.util.Random;
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

    private double size;
    private double value;

    public PlasticBottle() throws InvalidItemSizeException {
        super(ItemType.BOTTLE, ItemMaterial.PLASTIC, 0.0);
        selectRandomSize();

        if (DoubleStream.of(standardSizes).noneMatch(s -> s == size)) {
            throw new InvalidItemSizeException("Current size is not in standardSizes array.");
        }

        determineItemValue();
    }

    @Override
    public void selectRandomSize() {
        int rnd = new Random().nextInt(standardSizes.length);
        size = standardSizes[rnd];
    }

    @Override
    public void determineItemValue() {
        boolean small = size <= sizeComparisonValues[0];
        boolean medium = (size > sizeComparisonValues[0]) && (size <= sizeComparisonValues[1]);
        boolean large = size > sizeComparisonValues[1];

        if (small) {
            value = redemptionValue[0];
        } else if (medium) {
            value = redemptionValue[1];
        } else if (large) {
            value = redemptionValue[2];
        }
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public double getValue() {
        return value;
    }
}
