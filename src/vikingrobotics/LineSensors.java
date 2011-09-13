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

public class LineSensors implements Constants {

	Robot1777 r;
	DigitalInput left = new DigitalInput(LINESENSOR_LEFT);
	DigitalInput middle = new DigitalInput(LINESENSOR_MIDDLE);
	DigitalInput right = new DigitalInput(LINESENSOR_RIGHT);
	
	public LineSensors(Robot1777 r) {
		this.r = r;
	}
	
	void printUM() {

		int leftValue = left.get() ? 1 : 0;
		int middleValue = middle.get() ? 1 : 0;
		int rightValue = right.get() ? 1 : 0;

		r.uM.write(6, 1, "   " + leftValue);
		r.uM.write(6, 5, " | " + middleValue);
		r.uM.write(6, 9, " | " + rightValue);
	}
	
	void printUM2() {

		if      (!left.get())   { r.uM.write(3, 1, "1"); }
		else if (left.get())    { r.uM.write(3, 1, "0"); }
		if      (!middle.get()) { r.uM.write(3, 5, "1"); }
		else if (middle.get())  { r.uM.write(3, 5, "0"); }
		if      (!right.get())  { r.uM.write(3, 9, "1"); }
		else if (right.get())   { r.uM.write(3, 9, "0"); }
	}
	
	public int getL() {
		return left.get() ? 1 : 0;
	}
	
	public int getM() {
		return middle.get() ? 1 : 0;
	}
	
	public int getR() {
		return right.get() ? 1 : 0;
	}
	
	public boolean atL() {
		return left.get();
	}
	
	public boolean atM() {
		return middle.get();
	}
	
	public boolean atR() {
		return right.get();
	}
	
	public boolean atLMR() {
		return left.get() && middle.get() && right.get();
	}
	
	public boolean atLM() {
		return left.get() && middle.get();
	}
	
	public boolean atMR() {
		return middle.get() && right.get();
	}
	
	public boolean atLR() {
		return left.get() && right.get();
	}
	
	public boolean atCross() {
		if((atLMR()) || (atLM()) || (atMR())) {
			return true;
		} else {
			return false;
		}
	}

}
