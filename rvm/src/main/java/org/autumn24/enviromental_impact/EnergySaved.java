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

/**
 * This class represents how much energy recyclers recycling has saved.
 */
public class EnergySaved {
	/**
	 * Calculating how much energy recycler has saved when they have recycled aluminum cans.
	 *
	 * @param recycler the user whose recycling data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	public static double energySavedByRecyclingAluminiumCans(RegisteredRecycler recycler) {
		// https://www.madehow.com/Volume-2/Aluminum-Beverage-Can.html
		// Recycling aluminum can save 95% of energy costs used to produce aluminum
		final double energyUsedToProduceASingleAluminiumCanInMegaJoules = 1.5; // ~
		final double energyRequiredToRecycledASingleAluminiumCanInMegaJoules = 0.075;
		long cansRecycled = recycler.getTotalAluminiumCansRecycled();
		double energySavedByCan = (energyUsedToProduceASingleAluminiumCanInMegaJoules
				- energyRequiredToRecycledASingleAluminiumCanInMegaJoules);
		double energySaved = energySavedByCan * cansRecycled;
		return convertMJToKiloWattHour(energySaved);
	}

	private static double convertMJToKiloWattHour(double mjValueToBeConverted) {
		// 1 MJ = 0.27778 kwh
		return mjValueToBeConverted * 0.27778;
	}
}
