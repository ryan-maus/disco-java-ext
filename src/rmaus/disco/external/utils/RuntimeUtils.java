/**
Copyright (c) 2010, Allston Trading, LLC
All rights reserved.
 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:
 
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 
 * Neither the name of the author nor the names of its contributors
      may be used to endorse or promote products derived from this
      software without specific prior written permission.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

package rmaus.disco.external.utils;

import static rmaus.disco.external.utils.DiscoLogger.logThrowable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import rmaus.disco.external.map.MapFunction;
import rmaus.disco.external.reduce.ReduceFunction;

/**
 * A collection of utilities common to both map and reduce runs
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 *
 */
public class RuntimeUtils {
	
	/**
	 * Read the parameters from the external interface with the following
	 * protocol:
	 * <allParamSize><param1Size>_<param1Value>\n<param2Size>_<param2Value>\n ...
	 * 
	 * @return a java map of the ext_params python dict
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static Map<String, String> readParameters() throws IOException, NumberFormatException {
		String sizeStr = "";
		
		// Read until we hit a new line, this the length of the parameters
		// The newline separator is an arbitrary protocol
		char in;
		
		do {
			final byte[] buf = new byte[1];
			System.in.read(buf);
			in = (char)buf[0];
			sizeStr += ("" + in);
		} while (in != 10);

		int size = Integer.parseInt(sizeStr.trim()); // Total size of the params input
		final byte[] paramBytes = new byte[size];
		System.in.read(paramBytes);
		
		final Map<String, String> ret = new HashMap<String, String>();
		
		// Split on new lines, there are this many pairs
		final String[] pairs = new String(paramBytes).split("\n");
		for (final String pair : pairs) {
			final String[] pairVars = pair.split(" "); // Split on spaces, these are the sizes and keys/values
			ret.put(pairVars[1], pairVars[3]); // Ignore the sizes at index 0 and 2
		}
		
		return ret;
	}


	/**
	 * Load the {@link BaseFunction} via reflection. Any errors will cause
	 * a program shutdown.
	 * 
	 * @param params disco ext_params
	 * @param index the index of the parameter to use as the qualified class path
	 * @return the constructed map or reduce function
	 */
	@SuppressWarnings("unchecked")
	private static <T extends BaseFunction> T reflectFunction(
			final Map<String, String> params, final String clazz) {
		try {
			return (T) Class.forName(clazz).getConstructor(Map.class).newInstance(params);
			
		} catch (final Exception e) {
			// Reflection can throw a ton of exceptions, just catch-all and fail-fast
			logThrowable(e);
			System.exit(-1);
			return null;
		}
	}
	
	/**
	 * Load a map function by reflection.
	 * 
	 * @param params
	 *            disco ext_params
	 * @return the constructed map function
	 */
	public static MapFunction reflectMapFunction(final Map<String, String> params) {
		return RuntimeUtils.<MapFunction>reflectFunction(params, params.get("mapFunction"));
	}
	
	/**
	 * Load a reduce function by reflection
	 * 
	 * @param params
	 *            disco ext_params
	 * @return the constructed reduce function
	 */
	public static ReduceFunction reflectReduceFunction(final Map<String, String> params) {
		return RuntimeUtils.<ReduceFunction>reflectFunction(params, params.get("reduceFunction"));
	}

	/**
	 * A method to replace InputStream.available(), since it's unreliable.
	 * 
	 * @param in
	 *            the input stream in question
	 * @return true if there is a byte to be read, false otherwise
	 */
	public static boolean canRead(final InputStream in) throws IOException {
		in.mark(1); // Mark
		if (in.read() == -1) { return false; }
		in.reset(); // Recall
		return true;
	}
	
}
