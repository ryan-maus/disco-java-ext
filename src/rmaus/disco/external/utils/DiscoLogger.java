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

/**
 * A simple logger that conforms to the {@link DiscoEvent} protocol
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 *
 */
public class DiscoLogger {
	
	public static void log(final String message) {
		log(message, DiscoEvent.MSG);
	}
	
	public static void log(final String message, final DiscoEvent event) {
		final StringBuilder sb = new StringBuilder("**<");
		sb.append(event.getCode()).append("> ");
		sb.append(message);
		System.err.println(sb.toString().replace('\n', ' '));
		System.err.flush();
	}
	
	public static void logThrowable(final Throwable err) {
		logThrowable(err, DiscoEvent.ERR);
	}
	
	public static void logThrowable(final Throwable err, final DiscoEvent event) {
		final StringBuilder sb = new StringBuilder();
		sb.append(err.getCause() + " ");
		for (StackTraceElement ste : err.getStackTrace()) {
			sb.append(ste + " ");
		}
		
		log(sb.toString(), event);
	}

}
