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
 *
 * @author Neal
 */
public class Drive implements Constants {

	RobotDrive_ drive = new RobotDrive_(DRIVE_FRONT_LEFT, DRIVE_REAR_LEFT, DRIVE_FRONT_RIGHT, DRIVE_REAR_RIGHT);
//	RobotDrive drive14 = new RobotDrive(DRIVE_FRONT_LEFT, DRIVE_REAR_RIGHT);
//	RobotDrive drive32 = new RobotDrive(DRIVE_REAR_LEFT, DRIVE_FRONT_RIGHT);
	private final static double minimumJoystickValue = 0.2;
	Robot1777 r;
	
	/**
	 * Drive constructor
	 * 
	 */
	public Drive(Robot1777 r) {
		this.r = r;
	}

	
	/**
	 * Set the speed of all the motors.
	 * This is used once an appropriate drive setup function is called such as
	 * mecanumDrive().
	 * 
	 * @param fL Value for the front left motor.
	 * @param fR Value for the front right motor.
	 * @param rL Value for the rear left motor.
	 * @param rR Value for the rear right motor.
	 */
	protected void setSpeed(double fL, double fR, double rL, double rR) {

		drive.setSpeed(fL, fR, rL, rR);
//		drive14.tankDrive(fL, rR);
//		drive32.tankDrive(rL, fR);
	}

	
	/**
	 * Drive method for Mecanum wheeled robots.
	 *
	 * A method for driving with Mecanum wheeled robots. There are 4 wheels
	 * on the robot, arranged so that the front and back wheels are toed in 45 degrees.
	 * When looking at the wheels from the top, the roller axles should form an X across the robot.
	 *
	 * This is designed to be directly driven by joystick axes.
	 *
	 * @param X X value from the joystick. [-1.0..1.0]
	 * @param Y Y value from the joystick. [-1.0..1.0]
	 * @param Z Z value from the joystick. [-1.0..1.0]
	 */
	public void mecanumDrive(double iX, double iY, double iZ) {

		double X = deadZone(iX);
		double Y = deadZone(iY);
		double Z = deadZone(iZ);

		double frontLeft  = Y + Z + X;
		double frontRight = Y - Z - X;
		double rearLeft   = Y + Z - X;
		double rearRight  = Y - Z + X;

		r.uM.write(USER_MESSAGES_DRIVE, "X: " + roundDecimals(X) +
									 " | Y: " + roundDecimals(Y) +
									 " | Z: " + roundDecimals(Z));

		SmartDashboard.log(X, "X");
		SmartDashboard.log(Y, "Y");
		SmartDashboard.log(Z, "Z");

		
		double maxMagnitude = Math.abs(frontLeft);
		
		if(Math.abs(frontRight) > maxMagnitude)
			maxMagnitude = Math.abs(frontRight);
		
		if(Math.abs(rearLeft) > maxMagnitude)
			maxMagnitude = Math.abs(rearLeft);
		
		if(Math.abs(rearRight) > maxMagnitude)
			maxMagnitude = Math.abs(rearRight);
		
		if(maxMagnitude > 1) { 
			frontLeft /= maxMagnitude;
			frontRight /= maxMagnitude;
			rearLeft /= maxMagnitude;
			rearRight /= maxMagnitude;
		}
		
		setSpeed(frontLeft, frontRight, rearLeft, rearRight);
	}

	
	/**
	 * Provide tank steering using the stored robot configuration.
	 * @param left The value of the left stick.
	 * @param right The value of the right stick.
	 */
	public void tankDrive(double left, double right) {

//		drive14.tankDrive(left, right);
//		drive32.tankDrive(left, right);
	}

	
	/**
	 * Stop all the motors completely.
	 * 
	 */
	public void stop() {
		setSpeed(0, 0, 0, 0);
	}
	
	/**
	 * Round up decimals up to hundredth place to print on the User Messages.
	 * @param d Double that needs to be rounded up.
	 */
	private double roundDecimals(double d) {
		return Math.ceil(d * 1000.0) / 1000.0;
	}
	
	/**
	 * Overwrite joystick values in a way that 0.0-1.0 is proportional to 0.2-1.0
	 * @param x The joystick value that needs to be rounded up.
	 */
	private double deadZone(double x) {
		
		if (x > 1) return 1;
		if (Math.abs(x) < minimumJoystickValue) return 0;
		double scaledSlope = 1 / (1 - minimumJoystickValue);
		if (x > 0) return (x - minimumJoystickValue) * scaledSlope;
		return (x + minimumJoystickValue) * scaledSlope;
	}

}
