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

import java.math.BigDecimal;

/**
 * This class represents how much energy recyclers recycling has saved.
 */
class EnergySaved {
	/**
	 * Calculating how much energy recycler has saved when they have recycled aluminum cans.
	 *
	 * @param recycler the user whose recycling data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	protected static BigDecimal energySavedByRecyclingAluminiumCans(RegisteredRecycler recycler) {
		// https://www.madehow.com/Volume-2/Aluminum-Beverage-Can.html
		// Recycling aluminum can save 95% of energy costs used to produce aluminum
		final double producingVirginAluminumCan = 1.5; // ~ MJ
		final double recyclingAnAluminumCan = 0.075;
		return calculateEnergySaved(recycler, producingVirginAluminumCan, recyclingAnAluminumCan);
	}

	/**
	 * @param recycler the user whose recycling data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	protected static BigDecimal energySavedByRecyclingPlasticBottles(RegisteredRecycler recycler) {
		// https://www.greenbubble.com.au/blogs/news/how-much-energy-does-it-take-to-recycle-a-plastic-bottle
		// https://pacinst.org/wp-content/uploads/2013/02/bottled_water_and_energy3.pdf
		final double producingVirginPlasticBottle = 3.4; // ~ per litre bottle
		final double recyclingAPlasticBottle = 0.3; // ~ per litre bottle
		return calculateEnergySaved(recycler, producingVirginPlasticBottle, recyclingAPlasticBottle);
	}

	/**
	 * @param recycler the user whose recycling data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	protected static BigDecimal energySavedByRecyclingGlassBottles(RegisteredRecycler recycler) {
		// https://learnglassblowing.com/the-energy-used-to-make-a-glass-bottle/
		// www.norcalcompactors.net/glass-recycling/
		final double producingVirginGlassBottle = 1.8;
		final double recyclingAGlassBottle = 1.086;
		return calculateEnergySaved(recycler, producingVirginGlassBottle, recyclingAGlassBottle);
	}

	/**
	 * @param recycler recycler the user whose recycling data is used in the calculation
	 * @return energy saved (Kilowatt Hour)
	 */
	protected static BigDecimal energySavedInTotal(RegisteredRecycler recycler) {
		return energySavedByRecyclingPlasticBottles(recycler)
				.add(energySavedByRecyclingGlassBottles(recycler)
						.add(energySavedByRecyclingAluminiumCans(recycler))
				);
	}

	private static BigDecimal calculateEnergySaved(
			RegisteredRecycler recycler,
			double energyRequiredToProduce, // MJ
			double energyRequiredToRecycle  // MJ
	) {
		long cansRecycled = recycler.getTotalAluminiumCansRecycled();
		double energySavedByCan = energyRequiredToProduce - energyRequiredToRecycle;
		double energySaved = energySavedByCan * cansRecycled;
		return convertMJToKiloWattHour(energySaved);
	}

	private static BigDecimal convertMJToKiloWattHour(double mjValueToBeConverted) {
		// 1 MJ = 0.27778 kwh
		return BigDecimal.valueOf(mjValueToBeConverted * 0.27778);
	}
}
