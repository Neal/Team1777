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

import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Neal
 */
public interface Constants {

	// Joysticks
	public static final int kJoystick1 = 1;
	public static final int kJoystick2 = 2;
	public static final int kJoystick3 = 3;
	public static final int kJoystick4 = 4;
	public static final double kJoystickThreshold = 0.2;

	// Motor used by the camera
	public static final int kCamServoSlot = 1;
	public static final int kCamServoChannel = 9;

	// The claw mechanism
	public static final int kClawModule = 1;
	public static final int kClawChannel = 4;
	public static final Relay.Direction kClawDirection = Relay.Direction.kBoth;

	// Motor used by the arm
	public static final int kArmSlot = 1;
	public static final int kArmChannel = 5;

	// Drive motors
	public static final int kFrontLeftMotor = 1;
	public static final int kFrontRightMotor = 2;
	public static final int kRearLeftMotor = 3;
	public static final int kRearRightMotor = 4;

	// Line sensors
	public static final int kLineSensorModule = 1;
	public static final int kLineSensorLeft = 10;
	public static final int kLineSensorMiddle = 9;
	public static final int kLineSensorRight = 8;

	// User Messages
	public static final int kUserMessages1 = 1;
	public static final int kUserMessages2 = 2;
	public static final int kUserMessages3 = 3;
	public static final int kUserMessages4 = 4;
	public static final int kUserMessages5 = 5;
	public static final int kUserMessages6 = 6;
	public static final int[] kUserMessagesLine = {kUserMessages1, kUserMessages2, kUserMessages3, kUserMessages4, kUserMessages5, kUserMessages6};

	// Gyroscope slot
	public static final int kGyroSlot = 1;
	public static final int kGyroChannel = 2;

	// Compressor slot and relay
	public static final int kCompressorPressureSwitchSlot = 1;
	public static final int kCompressorPressureSwitchChannel = 2;
	public static final int kCompressorRelaySlot = 1;
	public static final int kCompressorRelayChannel = 2;
	
	// Gamepad axis
	public static final int kGamepadAxisLeftStickX = 1;
	public static final int kGamepadAxisLeftStickY = 2;
	public static final int kGamepadAxisShoulder = 3;
	public static final int kGamepadAxisRightStickX = 4;
	public static final int kGamepadAxisRightStickY = 5;
	public static final int kGamepadAxisDpad = 6;

	// Gamepad buttons
	public static final int kGamepadButtonA = 1; // Bottom Button
	public static final int kGamepadButtonB = 2; // Right Button
	public static final int kGamepadButtonX = 3; // Left Button
	public static final int kGamepadButtonY = 4; // Top Button
	public static final int kGamepadButtonShoulderL = 5;
	public static final int kGamepadButtonShoulderR = 6;
	public static final int kGamepadButtonBack = 7;
	public static final int kGamepadButtonStart = 8;
	public static final int kGamepadButtonLeftStick = 9;
	public static final int kGamepadButtonRightStick = 10;	// Need to double check if all buttons are correct.
	public static final int kGamepadButtonMode = -1;
	public static final int kGamepadButtonLogitech = -1;

	// Joystick axis
	public static final int kJoystickAxisX = 1;
	public static final int kJoystickAxisY = 2;
	public static final int kJoystickAxisZ = 3;

	// Joystick buttons
	public static final int kJoystickButton1 = 1;
	public static final int kJoystickButton2 = 2;
	public static final int kJoystickButton3 = 3;
	public static final int kJoystickButton4 = 4;
	public static final int kJoystickButton5 = 5;
	public static final int kJoystickButton6 = 6;
	public static final int kJoystickButton7 = 7;
	public static final int kJoystickButton8 = 8;
	public static final int kJoystickButton9 = 9;
	public static final int kJoystickButton10 = 10;
	public static final int kJoystickButton11 = 11;
	public static final int kJoystickButton12 = 12;
	public static final int kJoystickButtonTrigger = 1;

	// Setting for debug mode
	public static final boolean kDebugMode = true;

}
