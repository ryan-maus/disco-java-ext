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

import static rmaus.disco.external.utils.ByteUtils.bigEndianIntToLittleEndianBytes;
import static rmaus.disco.external.utils.ByteUtils.littleEndianBytesToBigEndianInt;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * An immutable key-value pair capable of being created from an input stream
 * (such as standard in) and written to an output stream (such as standard out)
 * as specified by the Disco external interface protocol.
 * 
 * @author Ryan Maus <ryan.maus AT gmail.com>
 */
public class DiscoKeyValuePair {
	
	private final String key;
	private final String value;
	
	/**
	 * Constructor.  Creates a key-value pair from the given input stream
	 * @param in
	 * @throws IOException
	 */
	public DiscoKeyValuePair(final InputStream in) throws IOException {
		this.key = readSizeValuePair(in);
		this.value = readSizeValuePair(in);
	}
	
	/**
	 * Constructor.  Creates a key-value pair from a key and value string
	 * @param key
	 * @param value
	 */
	public DiscoKeyValuePair(final String key, final String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the key of this key-value pair
	 * @return
	 */
	public String getKey() { return this.key; }
	
	/**
	 * Gets the value of this key-value pair
	 * @return
	 */
	public String getValue() { return this.value; }
	
	/** {@inheritDoc} */
	@Override
	public String toString() { return "(" + this.key + "," + this.value + ")"; }
	
	/**
	 * Read a <size><value> pair from given input stream
	 * @return value, as a string
	 * @throws IOException
	 */
	private String readSizeValuePair(final InputStream in) throws IOException {
		final byte[] valueSizeBuffer = new byte[4];
		in.read(valueSizeBuffer);
		final int valueSize = littleEndianBytesToBigEndianInt(valueSizeBuffer);
		
		if (valueSize != 0) {
			final byte[] valueBuffer = new byte[valueSize];
			in.read(valueBuffer);
			return new String(valueBuffer);
		} else {
			return "";
		}
	}
	
	/**
	 * Write a <size><value> pair to the given output stream
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	private static void writeSizeValuePair(final PrintStream out, final String value) throws IOException {
		final byte[] sizeOut = bigEndianIntToLittleEndianBytes(value.length());
		final byte[] bytesOut = value.getBytes();
		out.write(sizeOut);
		out.write(bytesOut);
	}
	
	/**
	 * Writes this key-value pair to given output stream in this format:
	 * <keySize><key><valueSize><value>
	 * @param out
	 * @throws IOException
	 */
	public void writeOut(final PrintStream out) throws IOException {
		writeSizeValuePair(out, key);
		writeSizeValuePair(out, value);
	}
	
}