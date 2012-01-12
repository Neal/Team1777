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

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Neal
 *
 */
public class Gamepad extends Joystick implements Constants {
	
	/**
	 * Gamepad contructor
	 * 
	 * @param port
	 */
	public Gamepad(int port) {
		super(port);
	}

	/**
	 * @param port
	 * @param numAxisTypes
	 * @param numButtonTypes
	 */
	public Gamepad(int port, int numAxisTypes, int numButtonTypes) {
		super(port, numAxisTypes, numButtonTypes);
	}


    /**
     * Get the button value for buttons 1 through 12.
     *
     * The buttons are returned in a single 16 bit value with one bit representing the state
     * of each button. The appropriate button is returned as a boolean value.
     *
     * @param button The button number to be read.
     * @return The state of the button.
     */
    public boolean getButton(int button) {
        return super.getRawButton(button);
    }

    
    /**
     * For the current joystick, return the axis determined by the argument.
     *
     * This is for cases where the joystick axis is returned programatically, otherwise one of the
     * previous functions would be preferable (for example getX()).
     *
     * @param axis The axis to read.
     * @return The value of the axis.
     */
	public double getAxis(int axis) {
		return deadZone(super.getRawAxis(axis));
	}

	
	/**
	 * Overwrite joystick values in a way that 0.0-1.0 is proportional to 0.2-1.0
	 * @param x The joystick value that needs to be rounded up.
	 */
	private double deadZone(double x) {
		
		if (x > 1) return 1;
		if (Math.abs(x) < kMinimumJoystickValue) return 0;
		double scaledSlope = 1 / (1 - kMinimumJoystickValue);
		if (x > 0) return (x - kMinimumJoystickValue) * scaledSlope;
		return (x + kMinimumJoystickValue) * scaledSlope;
	}

}
