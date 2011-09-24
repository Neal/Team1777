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
public class Drive implements Constants {

	vikingrobotics.RobotDrive drive = (vikingrobotics.RobotDrive) new RobotDrive(DRIVE_FRONT_LEFT, DRIVE_REAR_LEFT, DRIVE_FRONT_RIGHT, DRIVE_REAR_RIGHT);
	double frontLeft = 0.0, frontRight = 0.0, rearLeft = 0.0, rearRight = 0.0, max = 0.0;
	static double minimumJoystickValue = 0.2;
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
	void setSpeed(double fL, double fR, double rL, double rR) {

		drive.setSpeed(fL, fR, rL, rR);
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

		frontLeft  = deadZone(Y) + deadZone(Z) + deadZone(X);
		frontRight = deadZone(Y) - deadZone(Z) - deadZone(X);
		rearLeft   = deadZone(Y) + deadZone(Z) - deadZone(X);
		rearRight  = deadZone(Y) - deadZone(Z) + deadZone(X);

		r.uM.write(3, "X: " + roundDecimals(deadZone(X)) +
				   " | Y: " + roundDecimals(deadZone(Y)) +
				   " | Z: " + roundDecimals(deadZone(Z)));

		
		max = Math.abs(frontLeft);
		
		if(Math.abs(frontRight) > max)
			max = Math.abs(frontRight);
		
		if(Math.abs(rearLeft) > max)
			max = Math.abs(rearLeft);
		
		if(Math.abs(rearRight) > max)
			max = Math.abs(rearRight);
		
		if(max > 1) { 
			frontLeft /= max;
			frontRight /= max;
			rearLeft /= max;
			rearRight /= max;
		}
		
		setSpeed(frontLeft, frontRight, rearLeft, rearRight);
	}
	
    /**
     * Provide tank steering using the stored robot configuration.
     * @param leftStick The value of the left stick.
     * @param rightStick The value of the right stick.
     */
	void tankDrive(double leftStick, double rightStick) {
		
		drive.tankDrive(leftStick, rightStick);
	}
	
	/**
	 * Stop all the motors completely.
	 * 
	 */
	void stop() {
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
		
		if (Math.abs(x) < minimumJoystickValue) return 0;
		double scaledSlope = 1 / (1 - minimumJoystickValue);
		if (x > 0) return (x - minimumJoystickValue) * scaledSlope;
		return (x + minimumJoystickValue) * scaledSlope;
	}

}
