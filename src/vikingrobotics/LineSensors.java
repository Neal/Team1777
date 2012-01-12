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

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Neal
 */
public class LineSensors implements Constants {

	Robot1777 r;
	DigitalInput left, middle, right;
    int leftValue, middleValue, rightValue;
	

	/**
	 * LineSensors constructor
	 * 
	 */
	public LineSensors(Robot1777 r, int leftSensor, int middleSensor, int rightSensor) {
		this.r = r;
		System.out.println("- Initializing left line sensor on channel " + leftSensor);
		left = new DigitalInput(leftSensor);
		System.out.println("- Initializing middle line sensor on channel " + middleSensor);
		middle = new DigitalInput(middleSensor);
		System.out.println("- Initializing right line sensor on channel " + rightSensor);
		right = new DigitalInput(rightSensor);
		printUM();
	}

	
	/**
     * Update the values of the line tracking sensors.
     */
    private void updateSensorValues() {
        this.leftValue = left.get() ? 1 : 0;
        this.middleValue = middle.get() ? 1 : 0;
        this.rightValue = right.get() ? 1 : 0;
    }
	
	/**
	 * Prints all 3 line sensors' value on User Messages on the same line.
	 * 
	 */
	void printUM() {

		updateSensorValues();

		r.uM.write(USER_MESSAGES_LINESENSORS, 1, "   " + leftValue);
		r.uM.write(USER_MESSAGES_LINESENSORS, 5, " | " + middleValue);
		r.uM.write(USER_MESSAGES_LINESENSORS, 9, " | " + rightValue);
	}

	/**
	 * Returns the value of left line sensor in int.
	 * @return
	 */
	public int getL() {
		return left.get() ? 1 : 0;
	}

	/**
	 * Returns the value of middle line sensor in int.
	 * @return
	 */
	public int getM() {
		return middle.get() ? 1 : 0;
	}

	/**
	 * Returns the value of right line sensor in int.
	 * @return
	 */
	public int getR() {
		return right.get() ? 1 : 0;
	}

	/**
	 * Returns true if left line sensor is on, false otherwise.
	 * @return
	 */
	public boolean atL() {
		return left.get();
	}

	/**
	 * Returns true if middle line sensor is on, false otherwise.
	 * @return
	 */
	public boolean atM() {
		return middle.get();
	}

	/**
	 * Returns true if right line sensor is on, false otherwise.
	 * @return
	 */
	public boolean atR() {
		return right.get();
	}

	/**
	 * Returns true if left, middle and right line sensors are on, false otherwise.
	 * @return
	 */
	public boolean atLMR() {
		return left.get() && middle.get() && right.get();
	}

	/**
	 * Returns true if left and middle line sensors are on, false otherwise.
	 * @return
	 */
	public boolean atLM() {
		return left.get() && middle.get();
	}

	/**
	 * Returns true if middle and right line sensors are on, false otherwise.
	 * @return
	 */
	public boolean atMR() {
		return middle.get() && right.get();
	}
	
	/**
	 * Returns true if left and right line sensors are on, false otherwise.
	 * @return
	 */
	public boolean atLR() {
		return left.get() && right.get();
	}
	
	/**
	 * Returns true if all 3 line sensors light up (at cross), false otherwise.
	 * @return value
	 */
	public boolean atCross() {
		if((atLMR()) || (atLM()) || (atMR())) {
			return true;
		} else {
			return false;
		}
	}

}
