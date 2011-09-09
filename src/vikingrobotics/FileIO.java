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

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 * Reads and parses text files on the cRIO. This is useful for quickly adjusting tuning values using FTP, such as for PID.
 * Place the '#' character before comments.
 *
 * @author Kevin Wang
 */
public class FileIO {
	private static StringBuffer log = new StringBuffer("");

	/**
	 * Returns the contents of a file on the root directory of the cRIO, getting rid of comments and trimming whitespace.
	 * @param filename The name of the file to read.
	 * @return The contents of the file.
	 */
	private static String getFileContents(String filename) {
		String url = "file:///values/" + filename;
		String contents = "";
		try {
			FileConnection c = (FileConnection) Connector.open(url);
			BufferedReader buf = new BufferedReader(new InputStreamReader(c.openInputStream()));
			String line = "";

			boolean lineRead = false;
			while ((line = buf.readLine()) != null) {
				lineRead = false;
				if (line.charAt(0) != '#') {
					for (int i = 0; i < line.toCharArray().length; i++) {
						if (line.toCharArray()[i] == '#') {
							contents += line.substring(0, i).trim() + "\n";
							lineRead = true;
							break;
						}
					}
					if (lineRead == false) {
						contents += line + "\n";
					}
				}
			}
			c.close();
		}
		catch (IOException e) {
			reportError("FILEIO", e, "Could not get file contents");
		}
		return contents;
	}

	/**
	 * Create an array of doubles using values in a text file. Each line in the file corresponds to one element in the array.
	 * @param filename The name of the file to read.
	 * @return The array of values in the file.
	 */
	public static double[] getArray(String filename) {
		String raw = getFileContents(filename);
		StringTokenizer st = new StringTokenizer(raw);
		double[] array = new double[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			array[i++] = Double.parseDouble(st.nextToken());
		}
		return array;
	}

	/**
	 * Reports errors giving information on which class the error was in
	 * along with an error message from the catch block.
	 * @param context States in which class the error has occured.
	 * @param e Name of the exception.
	 * @param customMessage Displays the custom message in the catch block.
	 */
	public static void reportError(String context, Exception e, String customMessage) {
		try {
			String message = System.currentTimeMillis() + " [" + context + "] " + e.getMessage() + ": " + customMessage + "\n";
			log.append(message);

			if (Debug.DEBUG_MODE) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			// Do nothing
		}
	}

	/**
	 * Records a log of exceptions to competion.log.
	 */
	public static void writeLog() {
		String url = "file:///competition.log";
		try {
			FileConnection c = (FileConnection) Connector.open(url);
			OutputStreamWriter writer = new OutputStreamWriter(c.openOutputStream());
			writer.write(log.toString());
			c.close();
		} catch (Exception e) {
			// Do nothing
		}
	}
}