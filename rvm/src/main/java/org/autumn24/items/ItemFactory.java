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

package org.autumn24.items;

import org.autumn24.excpetion.InvalidItemSizeException;

import java.util.Random;

/**
 * Factory used to build different types of items, such as:
 * aluminium cans, glass bottles, plastic bottles.
 *
 * @author evnct
 * @since 1.0.0
 */
public class ItemFactory {
	/**
	 * @return Item
	 * @throws InvalidItemSizeException
	 */
	public Item createItem() throws InvalidItemSizeException {
		Random random = new Random();
		int itemToCreate = random.nextInt(3);

		return switch (itemToCreate) {
			case 0 -> new AluminiumCan();
			case 1 -> new GlassBottle();
			case 2 -> new PlasticBottle();
			default -> null;
		};
	}
}
