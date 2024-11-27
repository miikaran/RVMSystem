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

/**
 * A class that manages the stored application data.
 */
public class AppDataManager {
	/**
	 * Static Gson object to serialize/deserialize JSON data.
	 * Contains added custom user deserializer to allow returning
	 * correct user object from the user role.
	 */
	private static final Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(
					User.class,
					new UserDeserializer()
			)
			.create();

	private final String database;
	/**
	 * Stores the app data read from JSON.
	 */
	private AppData appData;

	/**
	 * Creates a new app data manager using the provided database.
	 *
	 * @param userDatabase The path to the database (JSON)
	 */
	public AppDataManager(String userDatabase) {
		this.database = userDatabase;
	}

	/**
	 * Gets the initialized gson object.
	 *
	 * @return A gson object.
	 */
	public static Gson getGson() {
		return gson;
	}

	/**
	 * Updates data from appData to the database.
	 */
	void updateAppDataToJson() {
		try (FileWriter writer = new FileWriter(getDatabase())) {
			getGson().toJson(getAppData(), writer);
		} catch (JsonIOException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Loads data from database to the appData.
	 */
	void loadJsonAppData() {
		try (FileReader reader = new FileReader(getDatabase())) {
			AppData appDataObj = getGson().fromJson(reader, AppData.class);
			switch (appDataObj) {
				case null -> System.out.println("Required data not found...");
				default -> setAppData(appDataObj);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Database not found: " + getDatabase());
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Gets configured database (JSON).
	 *
	 * @return A string representing the path to the database (JSON)
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Gets app data.
	 *
	 * @return An AppData object that contains the all stored application data.
	 */
	public AppData getAppData() {
		return appData;
	}

	/**
	 * Sets app data.
	 *
	 * @param appData Sets application data to provided AppData object.
	 */
	public void setAppData(AppData appData) {
		this.appData = appData;
	}
}
