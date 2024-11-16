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

import java.util.Objects;

/**
 * Represents the base of all charities to which can be donated to
 */
public abstract class Charity {
	private final String charityName;
	private final String charityCause;

	public Charity(String charityName, String charityCause) {
		this.charityName = charityName;
		this.charityCause = charityCause;
	}

	public String getCharityName() {
		return charityName;
	}

	public String getCharityCause() {
		return charityCause;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Charity charity = (Charity) o;
		return Objects.equals(charityName, charity.charityName) && Objects.equals(charityCause, charity.charityCause);
	}

	@Override
	public int hashCode() {
		return Objects.hash(charityName, charityCause);
	}
}
