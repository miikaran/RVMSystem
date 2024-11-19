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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.autumn24.data.AppData;
import org.autumn24.users.User;
import org.autumn24.utils.UserDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class AppDataManager {
	public static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(User.class, new UserDeserializer())
			.create();

	private final String userDatabase;
	public AppData appData;

	public AppDataManager(String userDatabase) {
		this.userDatabase = userDatabase;
	}

	public boolean updateAppDataToJson() {
		try (FileWriter writer = new FileWriter(userDatabase)) {
			gson.toJson(appData, writer);
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Database not found: " + userDatabase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean loadJsonAppData() {
		try (FileReader reader = new FileReader(userDatabase)) {
			AppData appDataObj = gson.fromJson(reader, AppData.class);
			if (appDataObj == null) {
				System.out.println("Required data not found...");
				return false;
			}
			appData = appDataObj;
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Database not found: " + userDatabase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
