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

package org.autumn24.charity.charities;

import org.autumn24.charity.Charity;

/**
 * Represents donate-able Alzheimer charity.
 *
 * @param name  Greenpeace Fund name
 * @param cause charity cause / what they do
 */
public record GreenpeaceFund(String name, String cause) implements Charity {
	/**
	 * Creates a new Greenpeace Fund instance
	 *
	 * @return Greenpeace Fund
	 */
	public static GreenpeaceFund createGreenpeace() {
		return new GreenpeaceFund(
				"Greenpeace Fund",
				"Fights for environmental justice and for our planet."
		);
	}
}
