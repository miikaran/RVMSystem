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

package org.autumn24.rvm;

import java.util.Date;
import java.util.UUID;

/**
 * A class representing receipt that can be received from a reverse vending machine.
 * @author miikaran
 * @since 1.0.0
 */
public class Receipt {
    public short numberOfProcessedAluminiumCans;
    public short numberOfProcessedGlassBottles;
    public short numberOfProcessePlasticBottles;

    private double totalValue;
    public String receiptId;

    public Receipt(short aluminiumCans, short glassBottles, short plasticBottles, double totalValue) {
        numberOfProcessedAluminiumCans = aluminiumCans;
        numberOfProcessedGlassBottles = glassBottles;
        numberOfProcessePlasticBottles = plasticBottles;
        this.totalValue = totalValue;
        receiptId = UUID.randomUUID().toString();
    }

    public double getTotalValue(){ return totalValue; }

    public void displayReceipt(){
        Date date = new Date();
        System.out.println();
        System.out.println("Date:               " + date);
        System.out.println("Receipt Id:         " + receiptId);
        System.out.println("===========RECYCLED ITEMS=============");
        System.out.println("Aluminium Cans:     " + numberOfProcessedAluminiumCans);
        System.out.println("Glass Bottles:      " + numberOfProcessedGlassBottles);
        System.out.println("Plastic Bottles:    " + numberOfProcessePlasticBottles);
        System.out.println("===========TOTAL VALUE================");
        System.out.println(totalValue + " €");
        System.out.println();
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "numberOfProcessedAluminiumCans=" + numberOfProcessedAluminiumCans +
                ", numberOfProcessedGlassBottles=" + numberOfProcessedGlassBottles +
                ", numberOfProcessePlasticBottles=" + numberOfProcessePlasticBottles +
                ", totalValue=" + totalValue +
                '}';
    }
}
