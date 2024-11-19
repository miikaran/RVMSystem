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
	private BigDecimal recyclingSessionTotalValue = BigDecimal.ZERO;
	private short recyclingSessionRecycledAmount = 0;
	private Map<ItemMaterial, Short> sessionRecycledAmounts = new EnumMap<>(ItemMaterial.class);

	public void addRecyclable(ItemMaterial material, short amount, BigDecimal determinedValue) {
		sessionRecycledAmounts.put(material, (short) (sessionRecycledAmounts.getOrDefault(material, (short) 0) + amount));
		recyclingSessionTotalValue = recyclingSessionTotalValue.add(determinedValue);
		recyclingSessionRecycledAmount += amount;
	}

	public short getTotalBottlesRecyled() {
		return recyclingSessionRecycledAmount;
	}

	public BigDecimal getTotalValue() {
		return recyclingSessionTotalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.recyclingSessionTotalValue = totalValue;
	}

	public void setTotalSessionRecycledAmount(short totalSessionRecycledAmount) {
		this.recyclingSessionRecycledAmount = totalSessionRecycledAmount;
	}

	public Map<ItemMaterial, Short> getSessionRecycledAmounts() {
		return sessionRecycledAmounts;
	}

	public void setSessionRecycledAmounts(Map<ItemMaterial, Short> sessionRecycledAmounts) {
		this.sessionRecycledAmounts = sessionRecycledAmounts;
	}

	@Override
	public String toString() {
		return "RecyclingSession{" +
				"sessionRecycledAmounts=" + sessionRecycledAmounts +
				", totalBottlesRecyled=" + recyclingSessionRecycledAmount +
				", totalValue=" + recyclingSessionTotalValue +
				'}';
	}
}
