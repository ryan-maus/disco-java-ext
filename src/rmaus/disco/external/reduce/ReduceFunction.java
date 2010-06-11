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

import java.util.List;
import java.util.Map;

import rmaus.disco.external.map.MapFunction;
import rmaus.disco.external.utils.BaseFunction;
import rmaus.disco.external.utils.DiscoKeyValuePair;

/**
 * The reduce function
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 *
 */
public abstract class ReduceFunction extends BaseFunction {
	
	/**
	 * Default constructor
	 * 
	 * @param parameters
	 *            disco ext_params
	 */
	public ReduceFunction(Map<String, String> parameters) {
		super(parameters);
	}
	
	/**
	 * Run the reduce function on all of the input pairs. This is different than
	 * the {@link MapFunction} in that the reduce doesn't have to generate a
	 * result key-value for each individual input key-value.
	 * 
	 * @param inputPairs
	 *            all of the key-value pairs that the map phase generated
	 * @return result list of key-value pairs
	 */
	public abstract List<DiscoKeyValuePair> generateResultPairs(final List<DiscoKeyValuePair> inputPairs);
	
}
