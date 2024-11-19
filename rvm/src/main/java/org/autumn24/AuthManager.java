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

package org.autumn24;

import org.autumn24.authentication.AuthStatus;
import org.autumn24.users.User;

import java.util.ArrayList;

public class AuthManager {
	public static AppDataManager appDataManager;
	private static AuthStatus authStatus;

	public AuthManager(AppDataManager appDataManager) {
		AuthManager.appDataManager = appDataManager;
		authStatus = AuthStatus.GUEST;
	}

	public static User getUserById(String userId) {
		ArrayList<User> userData = appDataManager.appData.getUserData();
		boolean found = userData.stream().anyMatch(user -> user.getUserId().equals(userId));
		return found ? userData.getFirst() : null;
	}

	public static void setAuthStatus(AuthStatus authStatus) {
		AuthManager.authStatus = authStatus;
	}

	public boolean isLoggedInAsEmployee() {
		return authStatus.equals(AuthStatus.ADMIN);
	}
	
	public boolean isLoggedInAsRecycler() {
		return authStatus.equals(AuthStatus.RECYCLER);
	}
}
