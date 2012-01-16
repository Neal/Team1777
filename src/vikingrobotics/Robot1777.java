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


/**
 *
 * @author Neal
 */
public class Robot1777 extends SimpleRobot implements Constants {

	public Arm arm;
	public Autonomous autonomous;
	public Camera cam;
	public Claw claw;
//	public Compressorr compressor;
	public Drive drive;
	public Gyro gyro;
//	public Joystick joystick2, joystick3, joystick4;
//	public Gamepad gamepad1;
	public Gamepad js1, js2, js3, js4;
//	public LineSensors LineSensors;
	public UserMessages uM;


	/**
	 * Robot-wide initialization code.
	 *
	 * Called exactly 1 time when the robot starts.
	 */
	public void robotInit() {

		System.out.println("\n[robot] Robot initializing...");
		
		uM = new UserMessages(this);
		autonomous = new Autonomous(this);
		cam = new Camera(this, kCamServoSlot, kCamServoChannel);
		arm = new Arm(this, kArmSlot, kArmChannel);
		claw = new Claw(this, kClawModule, kClawChannel, kClawDirection);
//		compressor = new Compressorr(this, kCompressorPressureSwitchSlot, kCompressorPressureSwitchChannel, kCompressorRelaySlot, kCompressorRelayChannel);
//		LineSensors = new LineSensors(this, kLineSensorModule, kLineSensorLeft, kLineSensorMiddle, kLineSensorRight);
		drive = new Drive(this, kFrontLeftMotor, kRearLeftMotor, kFrontRightMotor, kRearRightMotor);
		gyro = new Gyro(kGyroSlot, kGyroChannel);
		
		js1 = new Gamepad(kJoystick1);
		js2 = new Gamepad(kJoystick2);
		js3 = new Gamepad(kJoystick3);
		js4 = new Gamepad(kJoystick4);
		
		Watchdog.getInstance();
		SmartDashboard.init();
		SmartDashboard.useProfile("save.xml");

		System.out.println("[robot] Robot Ready!\n");
	}


	/**
	 * Autonomous mode.
	 *
	 * Called repeatedly while the robot is in the autonomous state.
	 */
	public void autonomous() {

		System.out.println("[mode] Autonomous started");
		uM.write(kUserMessages1, "Autonomous Mode");
//		autonomous.init();
		
		while (isAutonomous() && isEnabled()) {
			
			getWatchdog().feed();
//			autonomous.run();
			Timer.delay(0.005);
		}
		
		System.out.println("[mode] Autonomous stopped");
	}


	/**
	 * Operator control (tele-operated) mode.
	 * 
	 * Called repeatedly while the robot is in the operator-controlled state.
	 */
	public void operatorControl() {

		System.out.println("[mode] Tele-operated started");
		SmartDashboard.log("Tele-Operated", "MODE ");
		uM.write(kUserMessages1, "~ TELE-OP MODE ~");
		getWatchdog().setEnabled(true);
		gyro.reset();
		gyro.setSensitivity(0.007);
//		compressor.start();
		int gyroAngle = 0;

		
		while(isOperatorControl() && isEnabled()) {

			getWatchdog().feed();   // Damn watchdog gets hungry every 0.005 seconds.
//			LineSensors.printUM();
//			compressor.run();
			
			// Force compressor
//			if(js1.getButton(kGamepadButtonBack) && js1.getButton(kGamepadButtonShoulderL)) compressor.forceStop();
//			if(js1.getButton(kGamepadButtonBack) && js1.getButton(kGamepadButtonShoulderR)) compressor.forceStart();
			
			// Arm code
			arm.set(js1.getAxis(kGamepadAxisRightStickY) * 0.8);
			
			// Camera Code
			if(js1.getButton(kGamepadButtonLeftStick)) cam.setAngle(cam.getAngle() - 2);
			if(js1.getButton(kGamepadButtonRightStick)) cam.setAngle(cam.getAngle() + 2);
			
			// Claw Code
			if(js1.getButton(kGamepadButtonA) || js1.getButton(kGamepadButtonX)) claw.open();
			if(js1.getButton(kGamepadButtonB) || js1.getButton(kGamepadButtonY)) claw.close();
			
			// Gyro Code
			gyroAngle = (int) gyro.getAngle();
			if(js1.getButton(kGamepadButtonBack) || gyroAngle >= 360 || gyroAngle <= -360) gyro.reset();
			uM.write(kUserMessages6, "Gyro: " + gyroAngle);
			
			for(int b=1; b<13; b++) {
				if(js1.getButton(b)) Debug.println("[js1] Button pressed: " + b);
			}
			
			if(js1.getButton(kGamepadButtonStart)) {
				double s = 0.6;
				if(gyroAngle < -2) drive.setSpeed(s, s, s, s);
				else if(gyroAngle > 2) drive.setSpeed(-s, -s, -s, -s);
				else drive.setSpeed(0.0, 0.0, 0.0, 0.0);
			}
			else {
			// Driving Code
			drive.mecanumDrive(js1.getAxis(kGamepadAxisLeftStickX), -js1.getAxis(kGamepadAxisLeftStickY), -js1.getAxis(kGamepadAxisShoulder));
			}
			
			Timer.delay(0.005);   // Pause the loop for 0.005 seconds.
		}

		drive.stop();
		System.out.println("[mode] Tele-operated stopped");
	}


	/**
	 * Disabled mode.
	 *
	 * Called repeatedly while the robot is in the disabled state.
	 */
	public void disabled() {

		uM.write(kUserMessages1, "~ DISABLED MODE ~");
		System.out.println("[mode] Disabled");
//		compressor.stop();
		drive.stop();
		arm.stop();
		while(isDisabled())	getWatchdog().feed();
	}

}
