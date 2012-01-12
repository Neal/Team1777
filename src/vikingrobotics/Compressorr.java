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
import edu.wpi.first.wpilibj.SmartDashboard;

/**
 *
 * @author Neal
 */
public class Compressorr implements Constants {

	Robot1777 r;
	Compressor compressor;
	private boolean forceCompressorOff = false;
	
	/**
	 * Compressorr constructor
	 * 
	 */
	public Compressorr(Robot1777 r, int channel, int relay) {
		this.r = r;
		System.out.println("[cRIO] Initializing Compressor on channel " + channel + " and relay on channel " + relay);
		compressor = new Compressor(channel, relay);
		r.uM.write(4, "Compressor: Unknown");
	}

	/**
	 * Turn on the compressor and read the relay to automatically turn it
	 * off, then turn it back on when needed.
	 * 
	 */
	void run() {

		if(!forceCompressorOff) {
			
			if(!compressor.getPressureSwitchValue()) {
				r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Enabled");
				compressor.setRelayValue(Relay.Value.kForward);
			}
			else if(compressor.getPressureSwitchValue()) {
				r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Disabled");
				compressor.setRelayValue(Relay.Value.kOff);
			}
			else {
				r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Unknown.");
			}
			
		}
		else {
			r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Force stopped.");
			compressor.stop();
		}
	}
	
	/**
	 * Start the compressor. The is stopped by default and won't operate until starting it.
	 * 
	 */
	void start() {
		if(!forceCompressorOff)
			compressor.start();
	}
	
	/**
	 * Stop the compressor. This method will stop the compressor from turning on.
	 * 
	 */
	void stop() {
		if(!forceCompressorOff)
			compressor.stop();
	}
	
	/**
	 * Force stop the compressor. This method changes the boolean forceCompressorOff to true.
	 * 
	 */
	void forceStop() {
		forceCompressorOff = true;
		r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Force stopped.");
		compressor.stop();
	}
	
	/**
	 * Turn on compressor from Force stop. This method changes the boolean forceCompressorOff to false.
	 * 
	 */
	void forceStart() {
		forceCompressorOff = false;
		r.uM.write(USER_MESSAGES_COMPRESSOR, "Compressor: Force started.");
		this.run();
	}

}
