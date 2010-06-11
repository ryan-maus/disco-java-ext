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

import java.util.Map;

/**
 * Either a map function or a reduce function. Since Java doesn't have
 * first-class functions, this will have to suffice as a replacement for the
 * new_job(map=func ...) or new_job(reduce=func ...). Both map functions and
 * reduce functions take in a list of key-value pairs and generate a list of
 * key-value pairs, so there's no programmatic distinction between them.
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 * 
 */
public abstract class BaseFunction {
	
	protected final Map<String, String> parameters;
	
	/**
	 * Default constructor. When you override this constructor, you may not have
	 * additional arguments: that will break the reflection.  You should be using
	 * the ext_params to pass all information anyways.
	 * 
	 * @param parameters the ext_params map specified in new_job(...)
	 */
	public BaseFunction(final Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Get the Java representation of the ext_params dictionary
	 * 
	 * @return ext_params list of key-value pairs
	 */
	public final Map<String, String> getParameters() { return this.parameters; }

}
