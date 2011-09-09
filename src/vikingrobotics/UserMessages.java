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

public class UserMessages {
	
	DriverStationLCD uM = DriverStationLCD.getInstance();
	
	public void write(int line, String Message) {
		
		switch(line) {
		
				case 1: 
					uM.println(DriverStationLCD.Line.kMain6, 1, "                         ");
					uM.println(DriverStationLCD.Line.kMain6, 1, Message); 
					break;
				case 2:
					uM.println(DriverStationLCD.Line.kUser2, 1, "                         ");
					uM.println(DriverStationLCD.Line.kUser2, 1, Message); 
					break;
				case 3:
					uM.println(DriverStationLCD.Line.kUser3, 1, "                         ");
					uM.println(DriverStationLCD.Line.kUser3, 1, Message); 
					break;
				case 4:
					uM.println(DriverStationLCD.Line.kUser4, 1, "                         ");
					uM.println(DriverStationLCD.Line.kUser4, 1, Message); 
					break;
				case 5:
					uM.println(DriverStationLCD.Line.kUser5, 1, "                         ");
					uM.println(DriverStationLCD.Line.kUser5, 1, Message); 
					break;
				case 6:
					uM.println(DriverStationLCD.Line.kUser6, 1, "                         ");
					uM.println(DriverStationLCD.Line.kUser6, 1, Message); 
					break;
		}	
		
		uM.updateLCD();
	}
	
	public void write(int line, int start, String Message) {
		
		switch(line) {
				case 1:
					uM.println(DriverStationLCD.Line.kMain6, start, "                         ");
					uM.println(DriverStationLCD.Line.kMain6, start, Message); 
					break;
				case 2:
					uM.println(DriverStationLCD.Line.kUser2, start, "                         ");
					uM.println(DriverStationLCD.Line.kUser2, start, Message); 
					break;
				case 3:
					uM.println(DriverStationLCD.Line.kUser3, start, "                         ");
					uM.println(DriverStationLCD.Line.kUser3, start, Message); 
					break;
				case 4:
					uM.println(DriverStationLCD.Line.kUser4, start, "                         ");
					uM.println(DriverStationLCD.Line.kUser4, start, Message); 
					break;
				case 5:
					uM.println(DriverStationLCD.Line.kUser5, start, "                         ");
					uM.println(DriverStationLCD.Line.kUser5, start, Message); 
					break;
				case 6:
					uM.println(DriverStationLCD.Line.kUser6, start, "                         ");
					uM.println(DriverStationLCD.Line.kUser6, start, Message); 
					break;
		}
		
		uM.updateLCD();
	}

}
