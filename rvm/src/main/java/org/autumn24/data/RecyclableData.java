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

public class RecyclableData {
	private final short recyclingLimit;
	private short recyclingLimitCounter;
	private short totalRecycled;
	private transient short sessionRecycled;

	public RecyclableData(short recyclingLimit) {
		this.recyclingLimit = recyclingLimit;
	}

	public void addTotalRecycled(int amount) {
		totalRecycled += (short) amount;
	}

	public void addSessionRecycled(int amount) {
		sessionRecycled += (short) amount;
	}

	public void addToRecyclingLimitCounter(int amount) {
		recyclingLimitCounter += (short) amount;
	}

	public short getSessionRecycled() {
		return sessionRecycled;
	}

	public short getRecyclingLimitCounter() {
		return recyclingLimitCounter;
	}

	public void setRecyclingLimitCounter(short recyclingLimitCounter) {
		this.recyclingLimitCounter = recyclingLimitCounter;
	}

	public boolean isLimitReached() {
		return recyclingLimitCounter >= recyclingLimit;
	}

	@Override
	public String toString() {
		return "RecyclableData{" +
				"recyclingLimit=" + recyclingLimit +
				", recyclingLimitCounter=" + recyclingLimitCounter +
				", totalRecycled=" + totalRecycled +
				", sessionRecycled=" + sessionRecycled +
				'}';
	}
}
