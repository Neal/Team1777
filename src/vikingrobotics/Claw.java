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

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;

/**
 *
 * @author Neal
 */
public class Claw implements Constants {

	private Relay claw;
	private Robot1777 r;

	/**
	 * Claw constructor
	 * 
	 */
	public Claw(Robot1777 r, int channel, Direction direction) {
		this.r = r;
		System.out.println("[cRIO] Initializing Claw on channel " + channel);
		claw = new Relay(channel, direction);
		r.uM.write(USER_MESSAGES_CLAW, "Claw: Unknown");
	}

	/**
	 * Open claw and update User Messages.
	 * 
	 */
	public void open() {

		r.uM.write(USER_MESSAGES_CLAW, "Claw: OPEN");
		claw.set(Relay.Value.kForward);
	}
	
	/**
	 * Close claw and update User Messages.
	 * 
	 */
	public void close() {

		r.uM.write(USER_MESSAGES_CLAW, "Claw: CLOSE");
		claw.set(Relay.Value.kReverse);
	}

}
