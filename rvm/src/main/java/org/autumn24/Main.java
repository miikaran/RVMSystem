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

import org.autumn24.excpetion.InvalidItemSizeException;
import org.autumn24.items.PlasticBottle;
import org.autumn24.items.RecyclableItem;

/**
 * Holds the execution process
 * @author evnct
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Return assignment for autumn 2024 object oriented programming course");

        /* Possible process logic
           1. generate random amount of aluminium cans, glass bottles and plastic bottles (only once).
           2. generate those objects (having generated sizes and values)
         */

        try {
            RecyclableItem plasticBottle = new PlasticBottle();
        } catch (InvalidItemSizeException e) {
            throw new RuntimeException(e);
        }
    }
}