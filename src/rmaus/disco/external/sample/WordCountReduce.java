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

package rmaus.disco.external.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rmaus.disco.external.reduce.ReduceFunction;
import rmaus.disco.external.utils.DiscoKeyValuePair;

/**
 * A simple word-count reduce function
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 *
 */
public class WordCountReduce extends ReduceFunction {
	
	/**
	 * Default Constructor
	 * 
	 * @param params
	 *            disco ext_params
	 */
	public WordCountReduce(final Map<String, String> parameters) {
		super(parameters);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<DiscoKeyValuePair> generateResultPairs(final List<DiscoKeyValuePair> inputPairs) {
		final List<DiscoKeyValuePair> results = new ArrayList<DiscoKeyValuePair>();
		final Map<String, Integer> wordsToCount = new HashMap<String, Integer>();
		
		for (final DiscoKeyValuePair pair : inputPairs) {
			final String word = pair.getKey();
			final int count = Integer.parseInt(pair.getValue());
			
			if (wordsToCount.containsKey(pair.getKey())) {
				wordsToCount.put(word, wordsToCount.get(word) + count);
			} else {
				wordsToCount.put(word, count);
			}
		}
		
		for (final Map.Entry<String, Integer> entry : wordsToCount.entrySet()) {
			final String word = entry.getKey();
			final String count = entry.getValue().toString();
			results.add(new DiscoKeyValuePair(word, count));
		}
		
		return results;
	}

}
