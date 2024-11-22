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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.autumn24.data.AppData;
import org.autumn24.users.User;
import org.autumn24.utils.UserDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppDataManager {
	public static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(User.class, new UserDeserializer())
			.create();

	private final String database;
	public AppData appData;

	public AppDataManager(String userDatabase) {
		this.database = userDatabase;
	}

	public void updateAppDataToJson() {
		try (FileWriter writer = new FileWriter(database)) {
			gson.toJson(appData, writer);
		} catch (JsonIOException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void loadJsonAppData() {
		try (FileReader reader = new FileReader(database)) {
			AppData appDataObj = gson.fromJson(reader, AppData.class);
			if (appDataObj == null) {
				System.out.println("Required data not found...");
				return;
			}
			appData = appDataObj;
		} catch (FileNotFoundException e) {
			System.out.println("Database not found: " + database);
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
