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

import org.autumn24.users.Employee;
import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.util.ArrayList;

public class AuthManager {
	private final AppDataManager appDataManager;
	private AuthStatus authStatus;

	public AuthManager(AppDataManager appDataManager) {
		this.appDataManager = appDataManager;
		this.authStatus = AuthStatus.GUEST;
	}

	public boolean authenticateUser(String userId) {
		ArrayList<User> userData = appDataManager.appData.getUserData();
		// Check if user exists
		boolean authSuccess = userData.stream().anyMatch(user -> user.getUserId().equals(userId));
		if (!authSuccess) {
			return false;
		}
		// Update auth status
		User authUser = getUserById(userId);
		if (authUser instanceof Employee) {
			authStatus = AuthStatus.ADMIN;
		} else if (authUser instanceof RegisteredRecycler) {
			authStatus = AuthStatus.RECYCLER;
		} else {
			authStatus = AuthStatus.GUEST;
		}
		return true;
	}

	public User getUserById(String userId) {
		ArrayList<User> userData = appDataManager.appData.getUserData();
		return userData.stream().filter(user -> user.getUserId().equals(userId)).findFirst().get();
	}

	public boolean isLoggedInAsEmployee() {
		return authStatus.equals(AuthStatus.ADMIN);
	}

	public boolean isLoggedInAsRecycler() {
		return authStatus.equals(AuthStatus.RECYCLER);
	}
	
	public void setAuthStatus(AuthStatus authStatus) {
		this.authStatus = authStatus;
	}
}
