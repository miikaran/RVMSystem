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
 * Represents donate-able Amnesty International.
 *
 * @param name  Amnesty International name
 * @param cause charity cause / what they do
 */
public record AmnestyInternational(String name, String cause) implements Charity {
	/**
	 * Creates a new Amnesty International instance.
	 *
	 * @return Amnesty International
	 */
	public static AmnestyInternational createAmnestyInternational() {
		return new AmnestyInternational(
				"Amnesty International",
				"Helps to fight against human right abuses worldwide."
		);
	}
}
