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

package org.autumn24.utils;

import com.google.gson.*;
import org.autumn24.users.Employee;
import org.autumn24.users.GuestRecycler;
import org.autumn24.users.RegisteredRecycler;
import org.autumn24.users.User;

import java.lang.reflect.Type;


/**
 * A class that represents a JSON deserializer that returns correct object based on user role
 *
 * @author miikaran
 * @since 1.0.0
 */
public class UserDeserializer implements JsonDeserializer<User> {
	@Override
	public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String userRole = jsonObject.get("userRole").getAsString();
		return switch (userRole) {
			case "admin" -> jsonDeserializationContext.deserialize(jsonObject, Employee.class);
			case "guest" -> jsonDeserializationContext.deserialize(jsonObject, GuestRecycler.class);
			case "recycler" -> jsonDeserializationContext.deserialize(jsonObject, RegisteredRecycler.class);
			default -> throw new JsonParseException("Invalid user role: " + userRole);
		};
	}
}
