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
	Joystick joystick1, joystick2, joystick3, joystick4;
	Gamepad gamepad1;
	LineSensors LineSensors;
	Servo camServo;
	SmartDashboard smartDB;
	UserMessages uM;
	Watchdog watchdog;


	/**
	 * Robot-wide initialization code.
	 *
	 * Called exactly 1 time when the robot starts.
	 */
	public void robotInit() {

			// Yeah, I know I need to organise this a little -- who care, it works :P
			System.out.println("\nBooting up...");       // Don't worry, I know this doesn't do anything,
			System.out.println("Loading essentials..."); // It's just for debugging (to know when it reaches here)
			
			uM = new UserMessages(this);
			arm = new Arm(this, ARM_SLOT, ARM_DUMMY_SLOT);
			claw = new Claw(this, CLAW_SLOT, Relay.Direction.kBoth);
			cam = new Camera(this);
			drive = new Drive(this, DRIVE_FRONT_LEFT, DRIVE_REAR_LEFT, DRIVE_FRONT_RIGHT, DRIVE_REAR_RIGHT);
			autonomous = new Autonomous(this);
			compressor = new Compressorr(this, COMPRESSOR_CHANNEL, COMPRESSOR_RELAY);
			LineSensors = new LineSensors(this, LINESENSOR_LEFT, LINESENSOR_MIDDLE, LINESENSOR_RIGHT);
			
			camServo = new Servo(CAM_SERVO);
			gyro = new Gyro(GYRO_SLOT, GYRO_CHANNEL);
			gamepad1 = new Gamepad(1);
			joystick1 = new Joystick(JOYSTICK_1);
			joystick2 = new Joystick(JOYSTICK_2);
			joystick3 = new Joystick(JOYSTICK_3);
			joystick4 = new Joystick(JOYSTICK_4);
			
			Watchdog.getInstance();
			SmartDashboard.init();
			
			System.out.println("Robot Ready!\n\n");
	}


	/**
	 * Autonomous mode.
	 *
	 * Called repeatedly while the robot is in the autonomous state.
	 */
	public void autonomous() {

			System.out.println("~~ ENTERED AUTONOMOUS");
			uM.write(USER_MESSAGES_MODE, "Autonomous Mode");
			autonomous.init();

			while (isAutonomous() && isEnabled()) {
						
				getWatchdog().feed();
				autonomous.run();
				Timer.delay(0.005);
			}

	}


	/**
	 * Operator control (tele-operated) mode.
	 * 
	 * Called repeatedly while the robot is in the operator-controlled state.
	 */
	public void operatorControl() {

			System.out.println("~~ ENTERED OPERATOR CONTROL");
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
					
					boolean backButton = gamepad1.getButton(Gamepad_button_Back);
					
					// Force compressor
					if(joystick1.getRawButton(5) && joystick1.getRawButton(6)) compressor.forceStop();
					if(joystick1.getRawButton(7) && joystick1.getRawButton(8)) compressor.forceStart();
					
					// Arm code
					arm.set(joystick1.getRawAxis(5) * 0.8);
					
					// Camera Code
					if(joystick2.getRawButton(9) || joystick1.getRawButton(11))
						camServo.setAngle(camServo.getAngle() - 2);
					
					if(joystick2.getRawButton(10) || joystick1.getRawButton(12))
						camServo.setAngle(camServo.getAngle() + 2);
					
					// Claw Code
					if(joystick1.getRawButton(1) || joystick2.getRawButton(1) || joystick1.getRawButton(3) || joystick2.getRawButton(3))
						claw.open();
					
					if(joystick1.getRawButton(2) || joystick2.getRawButton(2) || joystick1.getRawButton(4) || joystick2.getRawButton(4))
						claw.close();
					
					// Gyro Code
					gyroAngle = (int) gyro.getAngle();            // All this works if you have a gyro plugged in.
					if(joystick1.getRawButton(10)) gyro.reset();  // But unfortunately, we never did that.
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
	}


	/**
	 * Disabled mode.
	 *
	 * Called repeatedly while the robot is in the disabled state.
	 */
	public void disabled() {

			uM.write(USER_MESSAGES_MODE, "~ DISABLED MODE ~");
			Debug.println("~ DISABLED ~");
			compressor.stop();
			drive.stop();
			arm.stop();
			while(isDisabled())	getWatchdog().feed();
	}

}
