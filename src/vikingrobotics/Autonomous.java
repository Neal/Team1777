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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;

/**
 *
 * @author Neal
 */
public class Autonomous implements Constants {
	
	private Robot1777 r;
	
	boolean bLM = false, bRM = false, bLR = false; // b = between | Left Middle Right
	boolean atL = false, atM = false, atR = false; // a = at | Left Middle Right
	
	double d1 = 0.8;
	double d2 = 0.2667;
	double d3 = 0.6;
	double d4 = 0.6;
	
	double startTime = 0;
	double currentTime = 0;
	
	/**
	 * Autonomous constructor
	 * 
	 */
	public Autonomous(Robot1777 r) {
		this.r = r;
	}
	
	public void init() {
		
		startTime = Timer.getFPGATimestamp();
		r.LineSensors.printUM();
		r.compressor.start();
		this.startTime = 0;
		this.currentTime = 0;
	}
	
	/**
	 * The main autonomous mode.
	 * 
	 */
	public void run() {

		this.currentTime = Timer.getFPGATimestamp() - startTime;
		
		if(currentTime > 5 && currentTime < 7)
			r.claw.close();

		if(currentTime > 8 && currentTime < 12)
			r.arm.set(-0.6);

		if(currentTime > 9 && currentTime < 12)
			r.drive.setSpeed(0.5, 0.5, 0.5, 0.5);
	
	}
	
	private void driveTillCross() {

		this.atL = r.LineSensors.atL();
		this.atM = r.LineSensors.atM();
		this.atR = r.LineSensors.atR();
		
		if(!r.LineSensors.atCross()) {
				
			if(atL || bLM) {
				r.drive.setSpeed(d2, d1, d2, d1);  bLM = true;
				if(atM) bLM = false;
			}
			else if (atM || bLR) {
				r.drive.setSpeed(d3, d3, d3, d3);  bLR = true;
				if(atL || atR) bLR = false;
			}
			else if (atR || bRM) {
				r.drive.setSpeed(d1, d2, d1, d2);  bRM = true;
				if(atM) bRM = false;
			}
			else {
				r.drive.setSpeed(d4, d4, d4, d4); // We should only get here if it doesn't know where it is, which most likely be when we start if they don't place it right.
			}
		}

	}
	
	/**
	 * Know if the time is up for autonomous or not.
	 * @param s Current time.
	 * @return true if time is up, false otherwise.
	 */
	private boolean timeUp(double s) {
		return s >= 15.00 ? true : false;
	}
	
}
