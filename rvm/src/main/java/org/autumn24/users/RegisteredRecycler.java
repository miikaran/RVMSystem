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
	private long totalPlasticBottlesRecycled;
	private long totalGlassBottlesRecycled;
	private long totalAluminiumCansRecycled;
	private long totalItemsRecycled;
	private BigDecimal totalValueRecycled;

	/* -------------------------------------------------------- */
	// TODO: Make this use a builder pattern due to many params
	/* -------------------------------------------------------- */
	public RegisteredRecycler(String userName, String userId, String userRole, long totalPlasticBottlesRecycled, long totalGlassBottlesRecycled, long totalAluminiumCansRecycled, long totalItemsRecycled, BigDecimal redeemedTotalValue) {
		super(userName, userId, userRole);
		this.totalPlasticBottlesRecycled = totalPlasticBottlesRecycled;
		this.totalGlassBottlesRecycled = totalGlassBottlesRecycled;
		this.totalAluminiumCansRecycled = totalAluminiumCansRecycled;
		this.totalItemsRecycled = totalItemsRecycled;
		this.totalValueRecycled = redeemedTotalValue;
	}

	public long getTotalItemsRecycled() {
		return totalItemsRecycled;
	}

	public void setTotalItemsRecycled() {
		this.totalItemsRecycled =
				totalPlasticBottlesRecycled
						+ totalGlassBottlesRecycled
						+ totalAluminiumCansRecycled;
	}

	public long getTotalAluminiumCansRecycled() {
		return totalAluminiumCansRecycled;
	}

	public void setTotalAluminiumCansRecycled(long totalAluminiumCansRecycled) {
		this.totalAluminiumCansRecycled = totalAluminiumCansRecycled;
	}

	public long getTotalGlassBottlesRecycled() {
		return totalGlassBottlesRecycled;
	}

	public void setTotalGlassBottlesRecycled(long totalGlassBottlesRecycled) {
		this.totalGlassBottlesRecycled = totalGlassBottlesRecycled;
	}

	public long getTotalPlasticBottlesRecycled() {
		return totalPlasticBottlesRecycled;
	}

	public void setTotalPlasticBottlesRecycled(long totalPlasticBottlesRecycled) {
		this.totalPlasticBottlesRecycled = totalPlasticBottlesRecycled;
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
				"totalPlasticBottlesRecycled=" + totalPlasticBottlesRecycled +
				", totalGlassBottlesRecycled=" + totalGlassBottlesRecycled +
				", totalAluminiumCansRecycled=" + totalAluminiumCansRecycled +
				", totalItemsRecycled=" + totalItemsRecycled +
				", totalValueRecycled=" + totalValueRecycled +
				'}';
	}
}
