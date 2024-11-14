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

import java.util.Random;

/**
 * All recyclable items implement this interface as long as items are generated
 * randomly. Methods of this interface should be called on their constructors at least
 * in their current implementation. 
 * @author evnct
 * @since 1.0.0
 */
public interface Item {
    default double selectRandomSize(double[] standardSizes) {
        int rnd = new Random().nextInt(standardSizes.length);
        return standardSizes[rnd];
    }
    void determineItemValue();
    double getDeterminedValue();
    ItemMaterial getItemMaterial();
}
