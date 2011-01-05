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

package rmaus.disco.external.map;

import java.util.List;
import java.util.Map;

import rmaus.disco.external.utils.BaseFunction;
import rmaus.disco.external.utils.DiscoKeyValuePair;

/**
 * The map function. It should be noted that the map/reduce functions are loaded
 * by reflection; during which, the constructor signature of the implementing
 * class is expected to be identical to this abstract class's constructor. Not
 * following this convention will cause errors in the reflection.
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 * 
 */
public abstract class MapFunction extends BaseFunction {

	/**
	 * Default constructor. Child class constructors must match this exactly,
	 * see above.
	 * 
	 * @param parameters
	 *            disco ext_params
	 */
	public MapFunction(final Map<String, String> parameters) {
		super(parameters);
	}
	
	/**
	 * Run the map function on the input key-value pair, generating output
	 * key-value pair(s). Called once for each key-value pair in this run.
	 * 
	 * @param inputPair
	 *            the input key-value pair
	 * @return new output key-value pair(s) corresponding to the inputPair
	 */
	public abstract List<DiscoKeyValuePair> generateResultPair(final DiscoKeyValuePair inputPair); 

}
