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

import java.util.Objects;


/**
 * Represents a user of the reverse vending machine
 */
public class User {
	private final String userName;
	private final String userId; // This would simulate the "QR-code" in the real machine
	private final String userRole;

	/**
	 * Creates a new user with the specified name, id and role
	 *
	 * @param userName User's username
	 * @param userId   User's uniquely identifiable id
	 * @param userRole User's defined role
	 */
	public User(String userName, String userId, String userRole) {
		this.userName = userName;
		this.userId = userId;
		this.userRole = userRole;
	}

	/**
	 * Gets user's username
	 *
	 * @return A string representing the user's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets user's role
	 *
	 * @return A string representing the user's role
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * Gets user's uniquely identifiable id
	 *
	 * @return A string representing the user's user id
	 */
	public String getUserId() {
		return userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userName, user.userName) && Objects.equals(userId, user.userId) && Objects.equals(userRole, user.userRole);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName, userId, userRole);
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", userId='" + userId + '\'' +
				", userRole='" + userRole + '\'' +
				'}';
	}
}
