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

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SmartDashboard;

/**
 * @author Neal
 *
 */
public class Arm implements Constants {

	Robot1777 r;
	RobotDrive arm;
	private double speed;

	/**
	 * Arm constructor
	 * 
	 */
	public Arm(Robot1777 r, int channel, int dummy_channel) {
		this.r = r;
		System.out.println("[cRIO] Initializing Arm on channel " + channel);
		arm = new RobotDrive(channel, dummy_channel);
		r.uM.write(USER_MESSAGES_ARM, "Arm: Unknown");
	}
	
	/**
	 * Sets the speed for the arm.
	 * @param speed The speed for the arm to set.
	 */
	void setSpeed(double speed) {
		
		arm.tankDrive(speed, 0);
	}

	/**
	 * Move the arm as per the speed and set a dead zone for joystick.
	 * @param speed The speed for the arm to set.
	 */
	public void set(double speed) {

		if(speed > -0.2 && speed < 0.2) 
			this.speed = 0;
		else
			this.speed = speed;
		
		
		if(getSpeed() < 0)
			r.uM.write(USER_MESSAGES_ARM, "Arm: Moving Upwards");
		
		else if(getSpeed() > 0)
			r.uM.write(USER_MESSAGES_ARM, "Arm: Moving Downwards");

		else if(getSpeed() == 0)
			r.uM.write(USER_MESSAGES_ARM, "Arm: Not moving");
		
		else
			r.uM.write(USER_MESSAGES_ARM, "Arm: Unknown.");

		setSpeed(getSpeed());
		SmartDashboard.log(getSpeed(), "Arm");
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void stop() {
		setSpeed(0);
	}

	void test() {

		double armSpeed = r.joystick2.getRawAxis(5) * 0.8;

		if(armSpeed <= -0.2 || armSpeed >= 0.2)
			arm.tankDrive(armSpeed, 0);
		
	}

}
