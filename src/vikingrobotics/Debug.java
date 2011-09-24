/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 *  Copyright (C) 2011 Team 1777, Viking Robotics.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package vikingrobotics;

/**
 * @author Neal
 *
 */
public class Debug implements Constants {

    /**
     * If in debug mode, prints specified string without a newline
     * @param s
     */
    public static void print(String s) {
        if (DEBUG_MODE) {
            System.out.print(s);
        }
    }

    public static void print(int s) {
        if (DEBUG_MODE) {
            System.out.print(s);
        }
    }

    public static void print(double s) {
        if (DEBUG_MODE) {
            System.out.print(s);
        }
    }

    public static void print(boolean s) {
        if (DEBUG_MODE) {
            System.out.print(s);
        }
    }

    /**
     * If in debug mode, prints specified string with a newline
     * @param s
     */
    public static void println(String s) {
        if (DEBUG_MODE) {
            System.out.println(s);
        }
    }

    public static void println(double s) {
        if (DEBUG_MODE) {
            System.out.println(s);
        }
    }

    public static void println(int s) {
        if (DEBUG_MODE) {
            System.out.println(s);
        }
    }

    public static void println(boolean s) {
        if (DEBUG_MODE) {
            System.out.println(s);
        }
    }

    /**
     * Returns true if in debug mode, false otherwise.
     * @return DEBUG_MODE
     */
    public static boolean getMode() {
        return DEBUG_MODE;
    }
}
