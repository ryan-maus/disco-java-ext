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

package rmaus.disco.external.reduce;

import static rmaus.disco.external.utils.ByteUtils.bigEndianIntToLittleEndianBytes;
import static rmaus.disco.external.utils.DiscoLogger.log;
import static rmaus.disco.external.utils.DiscoLogger.logThrowable;
import static rmaus.disco.external.utils.RuntimeUtils.canRead;
import static rmaus.disco.external.utils.RuntimeUtils.readParameters;
import static rmaus.disco.external.utils.RuntimeUtils.reflectReduceFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rmaus.disco.external.utils.DiscoKeyValuePair;

/**
 * Main class for the Java-python Disco external map
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 */
public class RunDiscoReduce {
	
	public static void main(final String[] args) {
		try {
			final Map<String, String> parameters = readParameters();
			final ReduceFunction reduceFunction = reflectReduceFunction(parameters);
			
			final List<DiscoKeyValuePair> pairsIn = new ArrayList<DiscoKeyValuePair>();
			
			while (canRead(System.in)) { // Get all the keys first
				final DiscoKeyValuePair pairIn = DiscoKeyValuePair.createFromInputStream(System.in);
				pairsIn.add(pairIn);
			}
			
			final List<DiscoKeyValuePair> results = reduceFunction.generateResultPairs(pairsIn);
			
			// Write out how many pairs there are in the results
			final byte[] numPairsOut = bigEndianIntToLittleEndianBytes(results.size());
			System.out.write(numPairsOut);
			
			// Write out each result key-value pair
			for (final DiscoKeyValuePair pairOut : results) {
				pairOut.writeOut(System.out);
			}
			
		} catch (final IOException ioe) {
			logThrowable(ioe);
			System.exit(-1);
			
		} catch (final NumberFormatException nfe) {
			log("Parameter input mismatch. Did you specify 'reduceFunction' in ext_params?");
			logThrowable(nfe);
			System.exit(-1);
			
		} catch (final Exception e) {
			// Catch any other exceptions that have propagated up to this level,
			// potentially from the mapping function 
			logThrowable(e);
			System.exit(-1);
		}
	}
	
}
