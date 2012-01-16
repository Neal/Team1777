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

//	private RobotDrive drive;
	private RobotDrive drive14;
	private RobotDrive drive32;
	private Robot1777 r;
	
	/**
	 * Drive constructor
	 * 
	 */
	public Drive(Robot1777 r, int frontLeft, int rearLeft, int frontRight, int rearRight) {
		this.r = r;
		Debug.println("[robot] Initializing front left motor on channel " + frontLeft);
		Debug.println("[robot] Initializing front right motor on channel " + frontRight);
		Debug.println("[robot] Initializing rear left motor on channel " + rearLeft);
		Debug.println("[robot] Initializing rear right motor on channel " + rearRight);
//		drive = new RobotDrive(leftMotorChannel, rightMotorChannel);
		drive14 = new RobotDrive(frontLeft, rearRight);
		drive32 = new RobotDrive(rearLeft, frontRight);
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

		drive14.tankDrive(fL, rR);
		drive32.tankDrive(rL, fR);
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
	public void mecanumDrive(double X, double Y, double Z) {

		double frontLeft  = Y + Z + X;
		double frontRight = Y - Z - X;
		double rearLeft   = Y + Z - X;
		double rearRight  = Y - Z + X;

		r.uM.write(kUserMessages2, "X " + roundDecimals(X) + " Y " + roundDecimals(Y) + " Z " + roundDecimals(Z));

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
	 * @param leftValue The value for the left motor.
	 * @param rightValue The value for the right motor.
	 */
	public void tankDrive(double leftValue, double rightValue) {

//		drive.tankDrive(leftValue, rightValue);
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
		return Math.ceil(d * 100.0) / 100.0;
	}

}
