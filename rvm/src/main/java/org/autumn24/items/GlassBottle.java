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
import java.util.Arrays;
import java.util.Objects;

/**
 * Represent a glass bottle that the RVM can accept.
 */
public class GlassBottle extends RecyclableItem implements Item {
	private final double[] standardSizes = {0.25, 0.33, 0.5, 0.75, 1.0, 1.5, 2.0};
	private final double litreComparisonValue = 1.0;
	private final double[] redemptionValue = {0.10, 0.40};
	private final double chosenSize = selectRandomSize(standardSizes);

	private BigDecimal determinedValue = BigDecimal.ZERO;

	protected GlassBottle() throws InvalidItemSizeException {
		super(ItemType.BOTTLE, ItemMaterial.GLASS, 0.0);
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

	@Override
	public void determineItemValue() {
		if (chosenSize < litreComparisonValue) {
			determinedValue = BigDecimal.valueOf(redemptionValue[0]);
		} else if (chosenSize >= litreComparisonValue) {
			determinedValue = BigDecimal.valueOf(redemptionValue[1]);
		}
	}

	@Override
	public String toString() {
		return "GlassBottle{" +
				"standardSizes=" + Arrays.toString(standardSizes) +
				", litreComparisonValue=" + litreComparisonValue +
				", redemptionValue=" + Arrays.toString(redemptionValue) +
				", chosenSize=" + chosenSize +
				", determinedValue=" + determinedValue +
				", itemStatus=" + getItemStatus() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		GlassBottle that = (GlassBottle) o;
		return Double.compare(chosenSize, that.chosenSize) == 0
				&& Objects.deepEquals(standardSizes, that.standardSizes)
				&& Objects.deepEquals(redemptionValue, that.redemptionValue)
				&& Objects.equals(determinedValue, that.determinedValue)
				&& Objects.equals(getItemStatus(), that.getItemStatus()
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.hashCode(standardSizes),
				litreComparisonValue,
				Arrays.hashCode(redemptionValue),
				chosenSize, determinedValue, getDeterminedValue()
		);
	}
}
