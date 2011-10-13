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
 * @author Neal
 *
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
	LineSensors LineSensors;
	Servo camServo;
	SmartDashboard smartDB;
	UserMessages uM;
	Watchdog watchdog;


	/**
	 * Robot-wide initialization code should go here.
	 *
	 * Called exactly 1 time when the competition starts.
	 */
	public void robotInit() {

			// Yeah, I know I need to organise this a little -- who care, it works :P
			System.out.println("\nBooting up...\nLoading essentials..."); // Don't worry, this is just for debug (to know when it reaches here)
			
			arm = new Arm(this);
			claw = new Claw(this);
			cam = new Camera(this);
			drive = new Drive(this);
			uM = new UserMessages(this);
			autonomous = new Autonomous(this);
			compressor = new Compressorr(this);
			LineSensors = new LineSensors(this);
			
			camServo = new Servo(CAM_SERVO);
			gyro = new Gyro(GYRO_SLOT, GYRO_CHANNEL);
			joystick1 = new Joystick(JOYSTICK_1);
			joystick2 = new Joystick(JOYSTICK_2);
			joystick3 = new Joystick(JOYSTICK_3);
			joystick4 = new Joystick(JOYSTICK_4);
			
			Watchdog.getInstance();
			SmartDashboard.init();
			LineSensors.printUM();
			cam.init();
			uM.init();
			
			System.out.println("Robot Ready!\n\n");
	}


	/**
	 * Autonomous should go here.
	 *
	 * Called repeatedly while the robot is in the autonomous state.
	 */
	public void autonomous() {

			System.out.println("~~ ENTERED AUTONOMOUS");
			uM.write(1, "Autonomous Mode");
			getWatchdog().setEnabled(true);

			while (isAutonomous() && isEnabled()) {
					
					// Just hoping everything works :)
					getWatchdog().feed();
					autonomous.run();
					Timer.delay(0.005);
			}
	}


	/**
	 * Operator control (tele-operated) code should go here.
	 * 
	 * Called repeatedly while the robot is in the operator-controlled state.
	 */
	public void operatorControl() {

			System.out.println("~~ ENTERED OPERATOR CONTROL");
			SmartDashboard.log("Tele-Operated", "MODE ");
			uM.write(1, "~ TELE-OP MODE ~");
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
					if(joystick1.getRawButton(5) && joystick1.getRawButton(6)) compressor.forceStop();
					if(joystick1.getRawButton(7) && joystick1.getRawButton(8)) compressor.forceStart();
					
					// Arm code
					arm.set(joystick1.getRawAxis(4) * 0.75); // I love this new method for arm :D (Hoping it works)

					
					// Camera Code
					if(joystick2.getRawButton(9) || joystick1.getRawButton(11))
						camServo.setAngle(camServo.getAngle() - 2);
					
					if(joystick2.getRawButton(10) || joystick1.getRawButton(12))
						camServo.setAngle(camServo.getAngle() + 2);

					
					// Claw Code
					if(joystick1.getRawButton(1) || joystick2.getRawButton(1) || joystick1.getRawButton(3) || joystick2.getRawButton(3)) {
						
						claw.open();
					}
					if(joystick1.getRawButton(2) || joystick2.getRawButton(2) || joystick1.getRawButton(4) || joystick2.getRawButton(4)) {

						claw.close();
					}
					

					// Gyro Code
					gyroAngle = (int) gyro.getAngle();            // All this works if you have a gyro plugged in.
					if(joystick1.getRawButton(10)) gyro.reset();  // But unfortunately we never did that.
					if(gyroAngle >= 360) gyro.reset();
					if(gyroAngle <=-360) gyro.reset();
					uM.write(5, "Gyro: " + gyroAngle);
					

					// Driving Code
					drive.mecanumDrive(joystick1.getX(), -joystick1.getY(), -joystick1.getZ() * 0.95);
					

					Timer.delay(0.005);   // Pause the loop for 0.005 seconds.
			}
	}


	/**
	 * Disabled should go here.
	 *
	 * Called repeatedly while the robot is in the disabled state.
	 */
	public void disabled() {

			compressor.stop();
			uM.write(1, "~ DISABLED MODE ~");
			LineSensors.printUM();
			while(isDisabled()) getWatchdog().feed();
	}

}
