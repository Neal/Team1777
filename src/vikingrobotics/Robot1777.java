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

	Arm arm;
	Autonomous autonomous;
	Camera cam;
	Claw claw;
	Compressorr compressor;
	Drive drive;
	Gyro gyro;
	Joystick joystick2, joystick3, joystick4;
	Gamepad gamepad1;
	LineSensors LineSensors;
	SmartDashboard smartDB;
	UserMessages uM;
	Watchdog watchdog;


	/**
	 * Robot-wide initialization code.
	 *
	 * Called exactly 1 time when the robot starts.
	 */
	public void robotInit() {

			System.out.println("\n[cRIO] Robot initializing...");
			
			uM = new UserMessages(this);
			autonomous = new Autonomous(this);
			cam = new Camera(this, CAM_SERVO);
			arm = new Arm(this, ARM_SLOT, ARM_DUMMY_SLOT);
			claw = new Claw(this, CLAW_SLOT, Relay.Direction.kBoth);
			compressor = new Compressorr(this, COMPRESSOR_CHANNEL, COMPRESSOR_RELAY);
			LineSensors = new LineSensors(this, LINESENSOR_LEFT, LINESENSOR_MIDDLE, LINESENSOR_RIGHT);
			drive = new Drive(this, DRIVE_FRONT_LEFT, DRIVE_REAR_LEFT, DRIVE_FRONT_RIGHT, DRIVE_REAR_RIGHT);
			
			gyro = new Gyro(GYRO_SLOT, GYRO_CHANNEL);
			gamepad1 = new Gamepad(JOYSTICK_1);
//			joystick1 = new Joystick(JOYSTICK_1);
			joystick2 = new Joystick(JOYSTICK_2);
			joystick3 = new Joystick(JOYSTICK_3);
			joystick4 = new Joystick(JOYSTICK_4);
			
			Watchdog.getInstance();
			SmartDashboard.init();
			
			System.out.println("[cRIO] Robot Ready!\n");
	}


	/**
	 * Autonomous mode.
	 *
	 * Called repeatedly while the robot is in the autonomous state.
	 */
	public void autonomous() {

			System.out.println("[mode] Autonomous started");
			uM.write(USER_MESSAGES_MODE, "Autonomous Mode");
			autonomous.init();

			while (isAutonomous() && isEnabled()) {
						
				getWatchdog().feed();
				autonomous.run();
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
			uM.write(USER_MESSAGES_MODE, "~ TELE-OP MODE ~");
			getWatchdog().setEnabled(true);
			gyro.reset();
			gyro.setSensitivity(0.007);
			compressor.start();
			int gyroAngle = 0;

			
			while(isOperatorControl() && isEnabled()) {

					getWatchdog().feed();   // Damn watchdog gets hungry every 0.005 seconds.
					LineSensors.printUM();
					compressor.run();
					
					// Force compressor
					if(gamepad1.getButton(Gamepad_button_Back) && gamepad1.getButton(Gamepad_button_R_Shoulder)) compressor.forceStop();
					if(gamepad1.getButton(Gamepad_button_Back) && gamepad1.getButton(Gamepad_button_L_Shoulder)) compressor.forceStart();
					
					// Arm code
					arm.set(gamepad1.getAxis(Gamepad_rightStick_Y) * 0.8);
					
					// Camera Code
					if(gamepad1.getButton(Gamepad_button_LeftStick) || gamepad1.getButton(11))
						cam.setAngle(cam.getAngle() - 2);
					
					if(gamepad1.getButton(Gamepad_button_RightStick) || gamepad1.getButton(12))
						cam.setAngle(cam.getAngle() + 2);
					
					// Claw Code
					if(gamepad1.getButton(Gamepad_button_A) || joystick2.getRawButton(1) ||
					   gamepad1.getButton(Gamepad_button_X) || joystick2.getRawButton(3))
							claw.open();
					
					if(gamepad1.getButton(Gamepad_button_B) || joystick2.getRawButton(2) ||
					   gamepad1.getButton(Gamepad_button_Y) || joystick2.getRawButton(4))
							claw.close();
					
					// Gyro Code
					gyroAngle = (int) gyro.getAngle();            // All this works if you have a gyro plugged in.
					if(gamepad1.getButton(10)) gyro.reset();  // But unfortunately, we never did that.
					if(gyroAngle >= 360) gyro.reset();
					if(gyroAngle <=-360) gyro.reset();
//					uM.write(5, "Gyro: " + gyroAngle); // Commented because there's no extra room in user messages.
					
					// Driving Code
//					drive.mecanumDrive(joystick1.getX(), -joystick1.getY(), -joystick1.getZ());
					drive.mecanumDrive(gamepad1.getAxis(Gamepad_leftStick_X),
									  -gamepad1.getAxis(Gamepad_leftStick_Y),
									  -gamepad1.getAxis(Gamepad_shoulderAxis));
					
					Timer.delay(0.005);   // Pause the loop for 0.005 seconds.
			}

			drive.stop();
			compressor.stop();
			System.out.println("[mode] Tele-operated stopped");
	}


	/**
	 * Disabled mode.
	 *
	 * Called repeatedly while the robot is in the disabled state.
	 */
	public void disabled() {

			uM.write(USER_MESSAGES_MODE, "~ DISABLED MODE ~");
			System.out.println("[mode] Disabled");
			compressor.stop();
			drive.stop();
			arm.stop();
			while(isDisabled())	getWatchdog().feed();
	}

}
