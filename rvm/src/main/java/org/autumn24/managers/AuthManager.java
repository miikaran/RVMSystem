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

package org.autumn24.managers;

import org.autumn24.authentication.AuthenticatedUser;
import org.autumn24.users.User;

import java.util.ArrayList;

/**
 * A class representing an authentication manager.
 * Used to manage authentication related stuff.
 */
public class AuthManager {
	public static AppDataManager appDataManager;
	private static AuthenticatedUser authenticatedUser;

	/**
	 * Creates a new authentication manager with given params.
	 *
	 * @param appDataManager Application data manager to use for fetching user data.
	 */
	public AuthManager(AppDataManager appDataManager) {
		AuthManager.appDataManager = appDataManager;
		authenticatedUser = AuthenticatedUser.GUEST;
	}

	/**
	 * Attempts to get user by its user id.
	 *
	 * @param userId Unique id of the user
	 * @return An object representing the user
	 */
	public static User getUserById(String userId) {
		ArrayList<User> userData = appDataManager.getAppData().getUserData();
		return userData.stream()
				.filter(user -> user.getUserId().equals(userId))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Sets currently authenticated user to provided one.
	 *
	 * @param authenticatedUser
	 */
	public static void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
		AuthManager.authenticatedUser = authenticatedUser;
	}

	/**
	 * Checks whether user is logged in as employee or not.
	 *
	 * @return Boolean value representing is the user logged in as employee or not.
	 */
	public boolean isLoggedInAsEmployee() {
		return authenticatedUser.equals(AuthenticatedUser.ADMIN);
	}

	/**
	 * Checks whether user is logged in as recycler or not.
	 *
	 * @return Boolean value representing is the user logged in as recycler or not.
	 */
	public boolean isLoggedInAsRecycler() {
		return authenticatedUser.equals(AuthenticatedUser.RECYCLER);
	}
}
