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

import org.autumn24.ApplicationManager;
import org.autumn24.charity.charities.AlzheimerAssociation;
import org.autumn24.charity.charities.AmnestyInternational;
import org.autumn24.charity.charities.Greenpeace;

public class CharityFactory {
	public Charity createCharity() {
		int chosenCharity = ApplicationManager.getUserAction();
		return switch (chosenCharity) {
			case 1 -> Greenpeace.createGreenpeace();
			case 2 -> AmnestyInternational.createAmnestyInternational();
			case 3 -> AlzheimerAssociation.createAlzheimerAssociation();
			default -> throw new IllegalArgumentException("Unexpected value: " + chosenCharity);
		};
	}
}
