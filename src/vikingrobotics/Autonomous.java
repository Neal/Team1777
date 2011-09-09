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

import edu.wpi.first.wpilibj.*;

public class Autonomous implements Const {
	
	Robot1777 r;
	
	boolean bLM = false, bRM = false, bLR = false; // b = between | Left Middle Right
	boolean atL = false, atM = false, atR = false; // a = at | Left Middle Right
	
	double d1 = 0.8;
	double d2 = 0.2667;
	double d3 = 0.6;
	double d4 = 0.6;
	
	double startTime = 0;
	
	public Autonomous(Robot1777 r) {
		this.r = r;
	}
	
	public void run() {

		double startTime = Timer.getFPGATimestamp();
		double currentTime = 0;

		while(r.isAutonomous() && r.isEnabled() && !timeUp(currentTime)) {

			currentTime = Timer.getFPGATimestamp() - startTime;
			
			if(r.LineSensors.getL() == 0) atL = true;
			else atL = false;
			if(r.LineSensors.getM() == 0) atM = true;
			else atM = false;
			if(r.LineSensors.getR() == 0) atR = true;
			else atR = false;

			r.LineSensors.printUM();

			if(!r.LineSensors.atCross()) {
				
					if(atL || bLM)
					{
							r.drive.setSpeed(d2, d1, d2, d1);  bLM = true;
							if(atM) bLM = false;
					}
					else if (atM || bLR)
					{
							r.drive.setSpeed(d3, d3, d3, d3);  bLR = true;
							if(atL || atR) bLR = false;
					}
					else if (atR || bRM)
					{
							r.drive.setSpeed(d1, d2, d1, d2);  bRM = true;
							if(atM) bRM = false;
					}
					else
					{
							r.drive.setSpeed(d4, d4, d4, d4);
					}
			}
			else {
				r.drive.stop();
			}

			if(currentTime > 7 && currentTime < 12)
			{
					r.arm.setSpeed(0.7);
			}
			if(currentTime > 10 && currentTime < 14)
			{
					r.drive.setSpeed(-0.6, -0.6, -0.6, -0.6);
			}
			if(currentTime < 1.3 && currentTime > 0.1) {
					r.arm.setSpeed(-0.8);
			} else {
					r.arm.setSpeed(0.0);
			}
		}
		
		if(timeUp(currentTime)) {
			r.drive.stop();
			r.arm.set(0);
		}
	}
	
	private boolean timeUp(double s) {
		return s >= 15.00 ? true : false;
	}
	
}
