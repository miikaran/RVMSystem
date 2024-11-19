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

package org.autumn24.users;

import java.math.BigDecimal;

public class RegisteredRecycler extends User implements Recycler {
	private short totalBottlesRecycled;
	private BigDecimal totalValueRecycled;

	public RegisteredRecycler(String userName, String userId, String userRole, short totalBottlesRecycled, BigDecimal redeemedTotalValue) {
		super(userName, userId, userRole);
		this.totalBottlesRecycled = totalBottlesRecycled;
		this.totalValueRecycled = redeemedTotalValue;
	}

	public short getTotalBottlesRecycled() {
		return totalBottlesRecycled;
	}

	public void setTotalBottlesRecycled(short totalBottlesRecycled) {
		this.totalBottlesRecycled = totalBottlesRecycled;
	}

	public BigDecimal getRedeemedTotalValue() {
		return totalValueRecycled;
	}

	public void setRedeemedTotalValue(BigDecimal redeemedTotalValue) {
		this.totalValueRecycled = redeemedTotalValue;
	}

	@Override
	public String toString() {
		return "RegisteredRecycler{" +
				"totalBottlesRecycled=" + totalBottlesRecycled +
				", totalValueRecycled=" + totalValueRecycled +
				'}';
	}
}
