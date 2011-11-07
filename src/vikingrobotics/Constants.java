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
 *
 * @author Neal
 */
public interface Constants {

	// Joysticks
	public static final int JOYSTICK_1 = 1;
	public static final int JOYSTICK_2 = 2;
	public static final int JOYSTICK_3 = 3;
	public static final int JOYSTICK_4 = 4;

	// Motor used with the camera
	public static final int CAM_SERVO = 9;

	// The claw mechanism
	public static final int CLAW_SLOT = 4;

	// Motor used to the arm
	public static final int ARM_SLOT = 5;
	public static final int ARM_DUMMY_SLOT = 6;

	// Drive motors
	public static final int DRIVE_FRONT_LEFT = 1;
	public static final int DRIVE_FRONT_RIGHT = 2;
	public static final int DRIVE_REAR_LEFT = 3;
	public static final int DRIVE_REAR_RIGHT = 4;

	// Line sensors
	public static final int LINESENSOR_LEFT = 10;
	public static final int LINESENSOR_MIDDLE = 9;
	public static final int LINESENSOR_RIGHT = 8;

	// User Messages
	public static final int USER_MESSAGES_MODE = 1;
	public static final int USER_MESSAGES_DRIVE = 2;
	public static final int USER_MESSAGES_LINESENSORS = 3;
	public static final int USER_MESSAGES_ARM = 4;
	public static final int USER_MESSAGES_CLAW = 5;
	public static final int USER_MESSAGES_COMPRESSOR = 6;

	// Gyroscope slots
	public static final int GYRO_SLOT = 1;
	public static final int GYRO_CHANNEL = 1;

	// Compressor slot
	public static final int COMPRESSOR_CHANNEL = 2;
	public static final int COMPRESSOR_RELAY = 2;

	// Setting for debug mode
	public static final boolean DEBUG_MODE = true;

	// A boolean which is always true
	public static final boolean tru = true;

}
