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

package org.autumn24.charity;

/**
 * Represents the base of all charities to which can be donated to
 */
public interface Charity {
	/**
	 * Name to implemented
	 *
	 * @return Charity name
	 */
	String name();

	/**
	 * Cause to be implemented
	 *
	 * @return Charity cause / their purpose
	 */
	String cause();
}
