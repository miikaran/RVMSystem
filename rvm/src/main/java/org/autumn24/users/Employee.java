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

/**
 * A class representing an employee user.
 * Has admin privileges by default.
 */
public class Employee extends User {
	private final String employeeId;
	public boolean canRepairRvm;

	/**
	 * Creates a new employee user with given params
	 *
	 * @param employeeId   // Id that can be used to identify the employee
	 * @param canRepairRvm // Value representing can the employee repair rvm's
	 * @param firstName    // First name of the employee
	 * @param lastName     // Last name of the employee
	 */
	public Employee(String employeeId, boolean canRepairRvm, String firstName, String lastName) {
		super(firstName + lastName, employeeId, "admin");
		this.employeeId = employeeId;
		this.canRepairRvm = canRepairRvm;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeId='" + employeeId + '\'' +
				", canRepairRvm=" + canRepairRvm +
				'}';
	}
}
