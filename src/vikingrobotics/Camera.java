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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author Neal
 */
public class Camera implements Constants {

		Robot1777 r;
		AxisCamera cam;
		
		/**
		 * Camera constructor
		 * 
		 */
		public Camera(Robot1777 r) {
			this.r = r;
		}

		/**
		 * Initialize the camera.
		 * 
		 * Set brightness; Rotate the camera to 180 degrees;
		 * Set resolution to 640 x 480; Set white balance to automatic;
		 * Set Exposure Control to automatic; Set Exposure priority
		 * to frame rate.
		 * 
		 */
		public void init() {

	        boolean cont = false;
	        Timer timer = new Timer();
	        timer.start();
	        cam = AxisCamera.getInstance();
	        while (!cont) {
	            try {
	                cam.getImage().free();
	                cont = true;
	            } catch (AxisCameraException e) {
	            	if(Debug.getMode())
	            		System.err.println("-- ERROR - AxisCamera error 1 --- " + e);
	            } catch (NIVisionException e) {
	            	if(Debug.getMode())
	            		System.err.println("-- ERROR - AxisCamera error 2 --- " + e);
	            }
	        }
	        timer.stop();
	        System.out.println("- Camera initialized in " + timer.get() + " seconds");
	        timer.reset();

	        timer.start();
			cam.writeBrightness(50);
			cam.writeRotation(AxisCamera.RotationT.k180);
			cam.writeResolution(AxisCamera.ResolutionT.k640x480);
			cam.writeWhiteBalance(AxisCamera.WhiteBalanceT.automatic);
			cam.writeExposureControl(AxisCamera.ExposureT.automatic);
			cam.writeExposurePriority(AxisCamera.ExposurePriorityT.frameRate);
	        timer.stop();
	        System.out.println("- Wrote camera settings in " + timer.get() + " seconds");
	        timer.reset();
	        
		}

}
