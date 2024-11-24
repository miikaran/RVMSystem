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

public class AuthManager {
	public static AppDataManager appDataManager;
	private static AuthenticatedUser authenticatedUser;

	public AuthManager(AppDataManager appDataManager) {
		AuthManager.appDataManager = appDataManager;
		authenticatedUser = AuthenticatedUser.GUEST;
	}

	public static User getUserById(String userId) {
		ArrayList<User> userData = appDataManager.getAppData().getUserData();
		return userData.stream()
				.filter(user -> user.getUserId().equals(userId))
				.findFirst()
				.orElse(null);
	}

	public static void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
		AuthManager.authenticatedUser = authenticatedUser;
	}

	public boolean isLoggedInAsEmployee() {
		return authenticatedUser.equals(AuthenticatedUser.ADMIN);
	}

	public boolean isLoggedInAsRecycler() {
		return authenticatedUser.equals(AuthenticatedUser.RECYCLER);
	}
}
