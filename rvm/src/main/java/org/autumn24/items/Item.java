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

import org.autumn24.exceptions.InvalidItemSizeException;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * All recyclable items implement this interface as long as items are generated
 * randomly. Methods of this interface should be called on their constructors at least
 * in their current implementation.
 */
public interface Item {
	/**
	 * Select random size from standard sizes array
	 *
	 * @param standardSizes double[]
	 * @return double
	 */
	default double selectRandomSize(double[] standardSizes) {
		int rnd = new Random().nextInt(standardSizes.length);
		return standardSizes[rnd];
	}

	void determineItemValue();

	/**
	 * Initializes item with select size and use it to determine the value of the deposited item
	 *
	 * @param chosenSize    double
	 * @param standardSizes double[]
	 * @throws InvalidItemSizeException Throwable
	 */
	default void initializeItem(double chosenSize, double[] standardSizes) throws InvalidItemSizeException {
		if (DoubleStream.of(standardSizes).noneMatch(s -> s == chosenSize)) {
			throw new InvalidItemSizeException("Current size is not in standardSizes array.");
		}

		determineItemValue();
	}

	BigDecimal getDeterminedValue();

	ItemMaterial getItemMaterial();

	ItemStatus getItemStatus();

	void setItemStatus(ItemStatus itemStatus);
}
