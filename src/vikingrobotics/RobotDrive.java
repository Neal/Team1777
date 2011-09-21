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
 * @author Neal
 *
 */
public class RobotDrive extends edu.wpi.first.wpilibj.RobotDrive {

	public RobotDrive(int frontLeftMotor, int rearLeftMotor,
			int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

	public void setSpeed(double frontLeft, double frontRear, double rearLeft, double rearRight) {
		
		m_frontLeftMotor.set(frontLeft);
		m_frontRightMotor.set(frontRear);
		m_rearLeftMotor.set(rearLeft);
		m_rearRightMotor.set(rearRight);
	}

}
