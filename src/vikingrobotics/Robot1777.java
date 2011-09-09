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
import edu.wpi.first.wpilibj.camera.AxisCamera;


/**
 * @author Neal
 *
 */
public class Robot1777 extends SimpleRobot implements Const {

	Arm arm;
	Claw claw;
	Gyro gyro;
	Drive drive;
	Servo camServo;
	AxisCamera cam;
	UserMessages uM;
	Watchdog watchdog;
	Joystick joystick1;
	Joystick joystick2;
	Joystick joystick3;
	Joystick joystick4;
	Autonomous autonomous;
	SmartDashboard smartDB;
	Compressorr compressor;
	LineSensors LineSensors;


	/**
	 * Robot-wide initialization code should go here.
	 *
	 * Called exactly 1 time when the competition starts.
	 */
	public void robotInit() {

			System.out.println("\nBooting up...\nLoading essentials..."); // Don't worry, this is just for debug (to know when it reaches here)
			arm = new Arm(this);
			drive = new Drive();
			claw = new Claw();
			uM = new UserMessages();
			compressor = new Compressorr(this);
			LineSensors = new LineSensors(this);
			gyro = new Gyro(GYRO_SLOT, GYRO_CHANNEL);
			camServo = new Servo(CAM_SERVO);
			joystick1 = new Joystick(JOYSTICK1);
			joystick2 = new Joystick(JOYSTICK2);
			joystick3 = new Joystick(JOYSTICK3);
			joystick4 = new Joystick(JOYSTICK4);

			autonomous = new Autonomous(this);
			AxisCamera.getInstance();
			Watchdog.getInstance();
			SmartDashboard.init();
			updateCamera();
			getWatchdog().setExpiration(0.2);
			System.out.println("Robot Ready!\n\n");
			uM.write(1, "Robot Ready!");
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
			boolean isTankDrive = false;

			// Loop until it's in Operator Control mode in the driver station.
			while(isOperatorControl() && isEnabled()) {

					getWatchdog().feed();
					LineSensors.printUM();
					compressor.run();

					
					// Arm code
					arm.set(joystick1.getY() * 0.75);

					
					// Camera Code
					if(joystick2.getRawButton(9) || joystick1.getRawButton(11))
					{
						camServo.setAngle(180);
					}
					if(joystick2.getRawButton(10) || joystick1.getRawButton(12))
					{
						camServo.setAngle(0);
					}

					
					// Claw Code
					if(joystick2.getRawButton(1) ||
					   joystick1.getRawButton(1) ||
					   joystick2.getRawButton(3) ||
					   joystick1.getRawButton(3)) {
						
						claw.open();
					}
					if(joystick2.getRawButton(2) ||
					   joystick1.getRawButton(2) ||
					   joystick2.getRawButton(4) ||
					   joystick1.getRawButton(4)) {

						claw.close();
					}
					

					// Gyro Code
					gyroAngle = (int) gyro.getAngle();
					if(joystick1.getRawButton(10)) gyro.reset();
					if(gyroAngle >= 360) gyro.reset();
					if(gyroAngle <=-360) gyro.reset();
					uM.write(5, 13, " | " + gyroAngle);
					

					// Driving Code
					if (isTankDrive) {
						drive.tankDrive(joystick1.getRawAxis(2), joystick1.getRawAxis(5));
					} else {
						drive.mecanumDrive(joystick1.getX(), -joystick1.getY(), -joystick1.getZ() * 0.95);
					}
					

					Timer.delay(0.005);
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

	public void updateCamera() {

			cam.writeBrightness(50);
			cam.writeRotation(AxisCamera.RotationT.k180);
			cam.writeResolution(AxisCamera.ResolutionT.k640x480);
			cam.writeWhiteBalance(AxisCamera.WhiteBalanceT.automatic);
			cam.writeExposureControl(AxisCamera.ExposureT.automatic);
			cam.writeExposurePriority(AxisCamera.ExposurePriorityT.frameRate);
	}
	
}
