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

import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that represents an inactivity timer that enables idle status aka (sleep-mode) for the target rvm.
 *
 * @author miikaran
 * @since 1.0.0
 */
public class InactivityTimer {

    public static int INACTIVITY_TIMEOUT_MS = 5000;
    public Timer inactivityTimer;
    public TimerTask inactivityTimerTask;
    public ReverseVendingMachine rvm;

    public InactivityTimer(ReverseVendingMachine rvm) {
        inactivityTimer = new Timer(true);
        this.rvm = rvm;
    }

    public void resetTimer() {
        if (inactivityTimerTask != null) {
            inactivityTimerTask.cancel();
        }
        inactivityTimerTask = new TimerTask() {
            @Override
            public void run() {
                onInactivityTimeout(rvm);
            }
        };
        inactivityTimer.schedule(inactivityTimerTask, INACTIVITY_TIMEOUT_MS);
    }

    public void onInactivityTimeout(ReverseVendingMachine rvm) {
        // This method could later be passed as a parameter to this class, but this is gud for now 😎
        System.out.println("\n\nMachine ID: '" + rvm.getRvmId() + "' - Entering sleep mode \uD83D\uDCA4");
        rvm.rvmStatus = ReverseVendingMachineStatus.IDLE;
        System.out.println("Press any key to wake it back up.");
    }

}
