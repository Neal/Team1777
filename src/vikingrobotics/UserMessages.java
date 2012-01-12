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

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;

/**
 * Prints the specified string on the User Messages section on the Driver Station.
 *
 * @author Neal
 */
public class UserMessages {
	
	DriverStationLCD uM = DriverStationLCD.getInstance();
	Robot1777 r;
	
	public static final Line[] line = {DriverStationLCD.Line.kMain6,
		DriverStationLCD.Line.kUser2,
		DriverStationLCD.Line.kUser3,
		DriverStationLCD.Line.kUser4,
		DriverStationLCD.Line.kUser5,
		DriverStationLCD.Line.kUser6};
	

	/**
	 * UserMessages constructor
	 * 
	 */
	public UserMessages(Robot1777 r) {
		this.r = r;
		init();
	}

	/**
	 * Prints the specified string on the User Messages starting from the begining.
	 * @param lineNumber Line number. Could be from 1 to 6.
	 * @param Message The message to be printed.
	 */
	public void write(int lineNumber, String Message) {
		
		write(lineNumber, 1, Message);
	}

	/**
	 * Prints the specified string on the User Messages starting from the specified starting column.
	 * @param lineNumber Line number. Could be from 1 to 6.
	 * @param startColumn The starting column to use.
	 * @param Message The message to be printed.
	 */
	public void write(int lineNumber, int startColumn, String Message) {

		uM.println(line[lineNumber-1], startColumn, "                         ");
		uM.println(line[lineNumber-1], startColumn, Message);
		uM.updateLCD();
	}

	/**
	 * Initialize User Messages. Clear all lines and update 1st line.
	 * 
	 */
	public void init() {
		
		clearAll();
		write(1, 1, "Robot Ready!");
	}

	/**
	 * Clear a specified line.
	 * @param lineNumber The line to be cleared.
	 */
	public void clear(int lineNumber) {
		
		write(lineNumber, 1, "");
	}
	
	/**
	 * Clear all lines.
	 * 
	 */
	public void clearAll() {
		
		for (int i = 1; i <= 6; i++)
			write(i, 1, "");
	}

}
