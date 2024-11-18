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

package org.autumn24.data;

import org.autumn24.rvm.enums.ReverseVendingMachineAuthStatus;
import org.autumn24.rvm.enums.ReverseVendingMachineFunctionalStatus;
import org.autumn24.rvm.enums.ReverseVendingMachinePowerStatus;
import org.autumn24.rvm.enums.ReverseVendingMachineStatus;

import java.math.BigDecimal;

public class RvmData {

	// Unique Id
	public String rvmId;

	// Session specific data
	public BigDecimal recyclingSessionTotalValue;
	public short recyclingSessionRecycledAmount;

	// Keeps count of recycled items
	public short numberOfAluminiumCansRecycled;
	public short numberOfGlassBottlesRecycled;
	public short numberOfPlasticBottlesRecycled;

	// Keeps count of recyclable specific limits
	public short aluminiumCanLimitCounter;
	public short glassBottleLimitCounter;
	public short plasticBottleLimitCounter;

	public ReverseVendingMachineStatus rvmStatus;
	public ReverseVendingMachineFunctionalStatus rvmFnStatus;
	public ReverseVendingMachinePowerStatus rvmPwStatus;
	public ReverseVendingMachineAuthStatus rvmAuthStatus;

	@Override
	public String toString() {
		return "RvmData{" +
				"rvmId='" + rvmId + '\'' +
				", recyclingSessionTotalValue=" + recyclingSessionTotalValue +
				", recyclingSessionRecycledAmount=" + recyclingSessionRecycledAmount +
				", numberOfAluminiumCansRecycled=" + numberOfAluminiumCansRecycled +
				", numberOfGlassBottlesRecycled=" + numberOfGlassBottlesRecycled +
				", numberOfPlasticBottlesRecycled=" + numberOfPlasticBottlesRecycled +
				", aluminiumCanLimitCounter=" + aluminiumCanLimitCounter +
				", glassBottleLimitCounter=" + glassBottleLimitCounter +
				", plasticBottleLimitCounter=" + plasticBottleLimitCounter +
				", rvmStatus=" + rvmStatus +
				", rvmFnStatus=" + rvmFnStatus +
				", rvmPwStatus=" + rvmPwStatus +
				", rvmAuthStatus=" + rvmAuthStatus +
				'}';
	}
}
