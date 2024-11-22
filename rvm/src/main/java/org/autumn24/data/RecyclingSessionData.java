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

package org.autumn24.data;

import org.autumn24.items.ItemMaterial;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class RecyclingSessionData {
	private final Map<ItemMaterial, Short> sessionRecycledAmounts = new EnumMap<>(ItemMaterial.class);
	private long recyclingSessionRecycledPlasticBottles = 0;
	private long recyclingSessionRecycledGlassBottles = 0;
	private long recyclingSessionRecycledAluminumBottles = 0;
	private BigDecimal recyclingSessionTotalValue = BigDecimal.ZERO;
	private short recyclingSessionRecycledAmount = 0; // short because our simulation limitations apply

	public void addRecyclable(ItemMaterial material, short amount, BigDecimal determinedValue) {
		sessionRecycledAmounts.put(material, (short) (sessionRecycledAmounts.getOrDefault(material, (short) 0) + amount));
		recyclingSessionTotalValue = recyclingSessionTotalValue.add(determinedValue);
		recyclingSessionRecycledAmount += amount;

		switch (material) {
			case ALUMINIUM -> recyclingSessionRecycledAluminumBottles++;
			case GLASS -> recyclingSessionRecycledGlassBottles++;
			case PLASTIC -> recyclingSessionRecycledPlasticBottles++;
		}
	}

	public long getRecyclingSessionRecycledPlasticBottles() {
		return recyclingSessionRecycledPlasticBottles;
	}

	public void setRecyclingSessionRecycledPlasticBottles(long recyclingSessionRecycledPlasticBottles) {
		this.recyclingSessionRecycledPlasticBottles = recyclingSessionRecycledPlasticBottles;
	}

	public long getRecyclingSessionRecycledGlassBottles() {
		return recyclingSessionRecycledGlassBottles;
	}

	public void setRecyclingSessionRecycledGlassBottles(long recyclingSessionRecycledGlassBottles) {
		this.recyclingSessionRecycledGlassBottles = recyclingSessionRecycledGlassBottles;
	}

	public long getRecyclingSessionRecycledAluminumBottles() {
		return recyclingSessionRecycledAluminumBottles;
	}

	public void setRecyclingSessionRecycledAluminumBottles(long recyclingSessionRecycledAluminumBottles) {
		this.recyclingSessionRecycledAluminumBottles = recyclingSessionRecycledAluminumBottles;
	}

	public BigDecimal getRecyclingSessionTotalValue() {
		return recyclingSessionTotalValue;
	}

	public void setRecyclingSessionTotalValue(BigDecimal recyclingSessionTotalValue) {
		this.recyclingSessionTotalValue = recyclingSessionTotalValue;
	}

	public short getRecyclingSessionRecycledAmount() {
		return recyclingSessionRecycledAmount;
	}

	public void setRecyclingSessionRecycledAmount(short recyclingSessionRecycledAmount) {
		this.recyclingSessionRecycledAmount = recyclingSessionRecycledAmount;
	}

	public Map<ItemMaterial, Short> getSessionRecycledAmounts() {
		return sessionRecycledAmounts;
	}

	@Override
	public String toString() {
		return "RecyclingSessionData{" +
				"sessionRecycledAmounts=" + sessionRecycledAmounts +
				", recyclingSessionRecycledPlasticBottles=" + recyclingSessionRecycledPlasticBottles +
				", recyclingSessionRecycledGlassBottles=" + recyclingSessionRecycledGlassBottles +
				", recyclingSessionRecycledAluminumBottles=" + recyclingSessionRecycledAluminumBottles +
				", recyclingSessionTotalValue=" + recyclingSessionTotalValue +
				", recyclingSessionRecycledAmount=" + recyclingSessionRecycledAmount +
				'}';
	}
}
