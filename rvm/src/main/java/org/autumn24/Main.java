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

import org.autumn24.managers.ApplicationManager;

/**
 * Holds the execution process
 *
 * @author evnct
 * @since 1.0.0
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Return assignment for autumn 2024 object oriented programming course");

		// Initialize application manager to handle menu actions
		ApplicationManager AppManager = new ApplicationManager();
		AppManager.run();
	}

}