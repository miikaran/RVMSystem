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
	private final String receiptId;

	public Receipt(short aluminiumCans, short glassBottles, short plasticBottles, BigDecimal totalValue) {
		numberOfProcessedAluminiumCans = aluminiumCans;
		numberOfProcessedGlassBottles = glassBottles;
		numberOfProcessedPlasticBottles = plasticBottles;
		this.totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
		receiptId = UUID.randomUUID().toString();
	}

	public short getNumberOfProcessedAluminiumCans() {
		return numberOfProcessedAluminiumCans;
	}

	public short getNumberOfProcessedGlassBottles() {
		return numberOfProcessedGlassBottles;
	}

	public short getNumberOfProcessedPlasticBottles() {
		return numberOfProcessedPlasticBottles;
	}

	public String getReceiptId() {
		return receiptId;
	}

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
