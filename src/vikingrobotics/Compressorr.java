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

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;

/**
 * @author Neal
 *
 */
public class Compressorr implements Constants {

	Robot1777 r;
	Compressor compressor = new Compressor(COMPRESSOR_CHANNEL, COMPRESSOR_RELAY);
	
	public Compressorr(Robot1777 r) {
		this.r = r;
	}
	
	void run() {

		if(!compressor.getPressureSwitchValue()) {
				r.uM.write(4, "Compressor: Enabled");
				compressor.setRelayValue(Relay.Value.kForward);
		}
		else if(compressor.getPressureSwitchValue()) {
				r.uM.write(4, "Compressor: Disabled");
				compressor.setRelayValue(Relay.Value.kOff);
		}
	}
	
	void start() {
		compressor.start();
	}
	
	void stop() {
		compressor.stop();
	}

}
