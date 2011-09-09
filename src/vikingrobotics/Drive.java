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

/**
 * @author Neal
 *
 */
public class Drive implements Const {

//	RobotDrive drive12 = new RobotDrive(DRIVE_FRONT_LEFT, DRIVE_FRONT_RIGHT);
//	RobotDrive drive34 = new RobotDrive(DRIVE_REAR_LEFT, DRIVE_REAR_RIGHT);
	vikingrobotics.RobotDrive drive = (vikingrobotics.RobotDrive) new RobotDrive(1, 3, 2, 4); // Will conflic with drive12 and drive34 while compile; comment either one.
	double frontLeft = 0.0;
	double frontRight = 0.0;
	double rearLeft = 0.0;
	double rearRight = 0.0;
	double max = 0.0;
    static double minimumJoystickValue = 0.2;
	
	public Drive() {

	}
	
	void setSpeed(double fL, double fR, double rL, double rR) {
		
//		drive12.tankDrive(fL, fR);
//		drive34.tankDrive(rL, rR);
		drive.setSpeed(fL, fR, rL, rR);
	}
	
	public void mecanumDrive(double X, double Y, double Z) {

		frontLeft  = setDeadZone(Y) + setDeadZone(Z) + setDeadZone(X);
		frontRight = setDeadZone(Y) - setDeadZone(Z) - setDeadZone(X);
		rearLeft   = setDeadZone(Y) + setDeadZone(Z) - setDeadZone(X);
		rearRight  = setDeadZone(Y) - setDeadZone(Z) + setDeadZone(X);

		max = Math.abs(frontLeft);
		if(Math.abs(frontRight) > max) max = Math.abs(frontRight);
		if(Math.abs(rearLeft) > max) max = Math.abs(rearLeft);
		if(Math.abs(rearRight) > max) max = Math.abs(rearRight);
		if(max > 1) { frontLeft /= max; frontRight /= max; rearLeft /= max; rearRight /= max; }
		
		setSpeed(frontLeft, frontRight, rearLeft, rearRight);
		
	}
	
	private double setDeadZone(double x) {
		
		if (Math.abs(x) < minimumJoystickValue) {
			return 0;
		}
		
		double scaledSlope = 1 / (1 - minimumJoystickValue);
		
		if (x > 0) {
			return (x - minimumJoystickValue) * scaledSlope;
		}
		return (x + minimumJoystickValue) * scaledSlope;
	}
	
	void tankDrive(double leftStick, double rightStick) {
		
		drive.tankDrive(leftStick, rightStick);
	}
	
	void stop() {
		setSpeed(0, 0, 0, 0);
	}

}
