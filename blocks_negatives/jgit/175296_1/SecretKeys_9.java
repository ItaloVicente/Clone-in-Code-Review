/*
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 *including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * </p>
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * </p>
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * </p>
 */
package org.eclipse.jgit.gpg.bc.internal.keys;


import java.io.IOException;
import java.io.InputStream;

import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.util.io.Streams;

/**
 * Utility functions for looking a S-expression keys. This class will move when
 * it finds a better home!
 * <p>
 * Format documented here:
 * </p>
 */
class SXprUtils {
	private static int readLength(InputStream in, int ch) throws IOException {
		int len = ch - '0';

		while ((ch = in.read()) >= 0 && ch != ':') {
			len = len * 10 + ch - '0';
		}

		return len;
	}

	static String readString(InputStream in, int ch) throws IOException {
		int len = readLength(in, ch);

		char[] chars = new char[len];

		for (int i = 0; i != chars.length; i++) {
			chars[i] = (char) in.read();
		}

		return new String(chars);
	}

	static byte[] readBytes(InputStream in, int ch) throws IOException {
		int len = readLength(in, ch);

		byte[] data = new byte[len];

		Streams.readFully(in, data);

		return data;
	}

	static S2K parseS2K(InputStream in) throws IOException {
		skipOpenParenthesis(in);

		readString(in, in.read());
		byte[] iv = readBytes(in, in.read());
		final long iterationCount = Long.parseLong(readString(in, in.read()));

		skipCloseParenthesis(in);

		S2K s2k = new S2K(HashAlgorithmTags.SHA1, iv, (int) iterationCount) {
			@Override
			public long getIterationCount() {
				return iterationCount;
			}
		};

		return s2k;
	}

	static void skipOpenParenthesis(InputStream in) throws IOException {
		int ch = in.read();
		if (ch != '(') {
			throw new IOException(
		}
	}

	static void skipCloseParenthesis(InputStream in) throws IOException {
		int ch = in.read();
		if (ch != ')') {
		}
	}
}
