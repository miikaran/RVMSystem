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

/**
 * A class representing a registered user.
 */
public class RegisteredRecycler extends User {
	private long totalPlasticBottlesRecycled;
	private long totalGlassBottlesRecycled;
	private long totalAluminiumCansRecycled;
	private long totalItemsRecycled;
	private BigDecimal totalValueRecycled;

	/**
	 * Creates a new registered user with given params
	 *
	 * @param userName                    Username of the user
	 * @param userId                      Unique id of the user
	 * @param userRole                    Role of the user
	 * @param totalPlasticBottlesRecycled Total plastic bottles recycled by the user
	 * @param totalGlassBottlesRecycled   Total glass bottles recycled by the user
	 * @param totalAluminiumCansRecycled  Total aluminium cans recycled by the user
	 * @param totalItemsRecycled          Total items recycled by the user
	 * @param redeemedTotalValue          Total value redeemed by the user
	 */
	public RegisteredRecycler(
			String userName,
			String userId,
			String userRole,
			long totalPlasticBottlesRecycled,
			long totalGlassBottlesRecycled,
			long totalAluminiumCansRecycled,
			long totalItemsRecycled,
			BigDecimal redeemedTotalValue
	) {
		super(userName, userId, userRole);
		this.totalPlasticBottlesRecycled = totalPlasticBottlesRecycled;
		this.totalGlassBottlesRecycled = totalGlassBottlesRecycled;
		this.totalAluminiumCansRecycled = totalAluminiumCansRecycled;
		this.totalItemsRecycled = totalItemsRecycled;
		this.totalValueRecycled = redeemedTotalValue;
	}

	/**
	 * Gets the total number of items user has recycled.
	 *
	 * @return A long value of the total items
	 */
	public long getTotalItemsRecycled() {
		return totalItemsRecycled;
	}

	/**
	 * Sets total items recycled by user to all diffent types combined.
	 */
	public void setTotalItemsRecycled() {
		this.totalItemsRecycled =
				totalPlasticBottlesRecycled
						+ totalGlassBottlesRecycled
						+ totalAluminiumCansRecycled;
	}

	/**
	 * Gets total aluminium cans recycled,
	 *
	 * @return A long value of total cans
	 */
	public long getTotalAluminiumCansRecycled() {
		return totalAluminiumCansRecycled;
	}

	/**
	 * Sets total recycled aluminium cans to given long value.
	 *
	 * @param totalAluminiumCansRecycled A long value to be set
	 */
	public void setTotalAluminiumCansRecycled(long totalAluminiumCansRecycled) {
		this.totalAluminiumCansRecycled = totalAluminiumCansRecycled;
	}

	/**
	 * Gets total glass bottles recycled
	 *
	 * @return A long value representing the total glass bottles recycled
	 */
	public long getTotalGlassBottlesRecycled() {
		return totalGlassBottlesRecycled;
	}

	/**
	 * Sets total glass bottles recycled to given long value.
	 *
	 * @param totalGlassBottlesRecycled A long value to be set
	 */
	public void setTotalGlassBottlesRecycled(long totalGlassBottlesRecycled) {
		this.totalGlassBottlesRecycled = totalGlassBottlesRecycled;
	}

	/**
	 * Gets total plastic bottles recycled.
	 *
	 * @return A long value representing the total plastic bottles recycled
	 */
	public long getTotalPlasticBottlesRecycled() {
		return totalPlasticBottlesRecycled;
	}

	/**
	 * Sets total plastic bottles recycled to given long value.
	 *
	 * @param totalPlasticBottlesRecycled A long value to be set
	 */
	public void setTotalPlasticBottlesRecycled(long totalPlasticBottlesRecycled) {
		this.totalPlasticBottlesRecycled = totalPlasticBottlesRecycled;
	}

	/**
	 * Gets total redeemed value.
	 *
	 * @return A BigDecimal value representing the total value redeemed
	 */
	public BigDecimal getRedeemedTotalValue() {
		return totalValueRecycled;
	}

	/**
	 * Sets total value redeemed to given BigDecimal value
	 *
	 * @param redeemedTotalValue A BigDecimal value to be set
	 */
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
