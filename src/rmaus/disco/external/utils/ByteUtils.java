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
 * A class containing the necessary byte-level conversion methods for
 * communicating with Disco's external interface.
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 */
public class ByteUtils {
	
	/**
	 * Converts a LE byte array to a BE integer
	 * @param b little-endian byte array
	 * @return big-endian integer
	 */
	public static int littleEndianBytesToBigEndianInt(final byte[] b) {
		int i = 0;
		int pos = 0;
		
		i += ((int)b[pos++] & 0xFF) << 0;
		i += ((int)b[pos++] & 0xFF) << 8;
		i += ((int)b[pos++] & 0xFF) << 16;
		i += ((int)b[pos++] & 0xFF) << 24;
		
		return i;
	}
	
	/**
	 * Converts a BE integer to a LE byte array
	 * @param i big-endian integer
	 * @return little endian byte array
	 */
	public static byte[] bigEndianIntToLittleEndianBytes(final int i) {
		final byte[] bytes = new byte[4];
		
		bytes[0] = (byte) (i & 0x00FF);
		bytes[1] = (byte) ((i >> 8) & 0x000000FF);
		bytes[2] = (byte) ((i >> 16) & 0x000000FF);
		bytes[3] = (byte) ((i >> 24) & 0x000000FF);
		
		return bytes;
	}
	
}
