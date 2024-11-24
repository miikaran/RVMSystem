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

package org.autumn24.enviromental_impact;

import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.math.BigDecimal;

/**
 * Used to start eco stat calculations.
 */
public class EcoStatSelector {
	/** Launches energy methods based
	 * @param chosenStat input by which a calculation method is called
	 * @param user the registered user whose data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	public static BigDecimal energySaved(int chosenStat, User user) {
		RegisteredRecycler recycler = (RegisteredRecycler) user;
		return switch (chosenStat) {
			case 1 -> EnergySaved.energySavedByRecyclingAluminiumCans(recycler);
			case 2 -> EnergySaved.energySavedByRecyclingGlassBottles(recycler);
			case 3 -> EnergySaved.energySavedByRecyclingPlasticBottles(recycler);
			case 4 -> EnergySaved.energySavedInTotal(recycler);
			default -> throw new IllegalArgumentException("Unexpected value: " + chosenStat);
		};
	}
}
