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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * A class representing receipt that can be received from a reverse vending machine.
 */
public class Receipt {
	private final BigDecimal totalValue;
	private final short numberOfProcessedAluminiumCans;
	private final short numberOfProcessedGlassBottles;
	private final short numberOfProcessedPlasticBottles;
	/**
	 * A unique id for each receipt.
	 */
	private final String receiptId;

	/**
	 * Creates a new receipt object from the given params.
	 *
	 * @param aluminiumCans  Amount of aluminium cans recycled.
	 * @param glassBottles   Amount of glass bottles recycled.
	 * @param plasticBottles Amount of plastic bottles recycled.
	 * @param totalValue     Total value of all the bottles.
	 */
	public Receipt(short aluminiumCans, short glassBottles, short plasticBottles, BigDecimal totalValue) {
		numberOfProcessedAluminiumCans = aluminiumCans;
		numberOfProcessedGlassBottles = glassBottles;
		numberOfProcessedPlasticBottles = plasticBottles;
		this.totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
		receiptId = UUID.randomUUID().toString();
	}

	/**
	 * Gets number of recycled aluminium cans.
	 *
	 * @return A number of recycled aluminium cans
	 */
	public short getNumberOfProcessedAluminiumCans() {
		return numberOfProcessedAluminiumCans;
	}

	/**
	 * Gets a number of recycled glass bottles.
	 *
	 * @return A numer of recycled glass bottles.
	 */
	public short getNumberOfProcessedGlassBottles() {
		return numberOfProcessedGlassBottles;
	}

	/**
	 * Gets a number of recycled plastic bottles.
	 *
	 * @return A number of recycled plastic bottles.
	 */
	public short getNumberOfProcessedPlasticBottles() {
		return numberOfProcessedPlasticBottles;
	}

	/**
	 * Gets the unique id set for each receipt.
	 *
	 * @return A string representing the unique id.
	 */
	public String getReceiptId() {
		return receiptId;
	}

	/**
	 * Gets the total value of all the recycled bottles.
	 *
	 * @return A BigDecimal value of the recycled bottles.
	 */
	public BigDecimal getTotalValue() {
		return totalValue;
	}

	@Override
	public String toString() {
		return "Receipt{" +
				"numberOfProcessedAluminiumCans=" + numberOfProcessedAluminiumCans +
				", numberOfProcessedGlassBottles=" + numberOfProcessedGlassBottles +
				", numberOfProcessedPlasticBottles=" + numberOfProcessedPlasticBottles +
				", totalValue=" + totalValue +
				'}';
	}
}
