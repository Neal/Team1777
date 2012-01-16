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
 *
 * @author Neal
 */
public class Compressorr implements Constants {

	private Robot1777 r;
	private Compressor compressor;
	private boolean forceCompressorOff = false;
	
	/**
	 * Compressorr constructor
	 * 
	 */
	public Compressorr(Robot1777 r, int pressureSwitchSlot, int pressureSwitchChannel, int compressorRelaySlot, int compressorRelayChannel) {
		this.r = r;
		System.out.println("[robot] Initializing Compressor on channel " + pressureSwitchChannel + " and relay on channel " + compressorRelayChannel);
		compressor = new Compressor(pressureSwitchSlot, pressureSwitchChannel, compressorRelaySlot, compressorRelayChannel);
		updateUserMessages("Compressor: Unknown");
	}

	/**
	 * Turn on the compressor and read the relay to automatically turn it
	 * off, then turn it back on when needed.
	 * 
	 */
	void run() {

		if(!forceCompressorOff) {
			
			if(!compressor.getPressureSwitchValue()) {
				updateUserMessages("Compressor: Enabled");
				compressor.setRelayValue(Relay.Value.kForward);
			}
			else if(compressor.getPressureSwitchValue()) {
				updateUserMessages("Compressor: Disabled");
				compressor.setRelayValue(Relay.Value.kOff);
			}
			else {
				updateUserMessages("Compressor: Unknown.");
			}
			
		}
		else {
			updateUserMessages("Compressor: Force stopped.");
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
		updateUserMessages("Compressor: Force stopped.");
		compressor.stop();
	}
	
	/**
	 * Turn on compressor from Force stop. This method changes the boolean forceCompressorOff to false.
	 * 
	 */
	void forceStart() {
		forceCompressorOff = false;
		updateUserMessages("Compressor: Force started.");
		this.run();
	}
	
	public void updateUserMessages(String message) {
		r.uM.write(kUserMessages6, message);
	}

}
