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

public class RegisteredRecycler extends User implements Recycler {
	private short totalBottlesRecycled;
	private short redeemedTotalValue;

	public RegisteredRecycler(String userName, String userId, String userRole, short totalBottlesRecycled, short redeemedTotalValue) {
		super(userName, userId, userRole);
		this.totalBottlesRecycled = totalBottlesRecycled;
		this.redeemedTotalValue = redeemedTotalValue;
	}

	public short getTotalBottlesRecycled() {
		return totalBottlesRecycled;
	}

	public void setTotalBottlesRecycled(short totalBottlesRecycled) {
		this.totalBottlesRecycled = totalBottlesRecycled;
	}

	public short getRedeemedTotalValue() {
		return redeemedTotalValue;
	}

	public void setRedeemedTotalValue(short redeemedTotalValue) {
		this.redeemedTotalValue = redeemedTotalValue;
	}

	@Override
	public String toString() {
		return "RegisteredRecycler{" +
				"totalBottlesRecycled=" + totalBottlesRecycled +
				", redeemedTotalValue=" + redeemedTotalValue +
				'}';
	}
}
