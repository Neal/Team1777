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

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.can.CANNotInitializedException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Neal
 */
public class RobotDrive_ extends RobotDrive {

	/**
	 * Constructor for RobotDrive with 4 motors specified with channel numbers.
	 * Set up parameters for a four wheel drive system where all four motor
	 * pwm channels are specified in the call.
	 * This call assumes Jaguars for controlling the motors.
	 * @param frontLeftMotor Front left motor channel number on the default digital module
	 * @param rearLeftMotor Rear Left motor channel number on the default digital module
	 * @param frontRightMotor Front right motor channel number on the default digital module
	 * @param rearRightMotor Rear Right motor channel number on the default digital module
	 */
	public RobotDrive_(int frontLeftMotor, int rearLeftMotor,
			int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}


	/** 
	 * Set the speed of all the 4 motors.
	 * This is used once an appropriate drive setup function is called such as
	 * mecanumDrive().
	 * 
	 * @param frontLeft Value for the Front left motor
	 * @param frontRight Value for the Front Right motor 
	 * @param rearLeft Value for the Rear Left motor
	 * @param rearRight Value for the Rear Right motor
	 */
	public void setSpeed(double frontLeft, double frontRight, double rearLeft, double rearRight) {
		

        byte syncGroup = (byte)0x80;
        
		m_frontLeftMotor.set(frontLeft, syncGroup);
		m_frontRightMotor.set(frontRight, syncGroup);
		m_rearLeftMotor.set(rearLeft, syncGroup);
		m_rearRightMotor.set(rearRight, syncGroup);

        if (m_isCANInitialized) {
            try {
                CANJaguar.updateSyncGroup(syncGroup);
            } catch (CANNotInitializedException e) {
                m_isCANInitialized = false;
            } catch (CANTimeoutException e) {}
        }

        if (m_safetyHelper != null) m_safetyHelper.feed();
	}

}
