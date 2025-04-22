package org.eclipse.jgit.gpg.bc.internal.keys;


import java.io.IOException;
import java.io.InputStream;

import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.util.io.Streams;

class SXprUtils {
	private static int readLength(InputStream in
		int len = ch - '0';

		while ((ch = in.read()) >= 0 && ch != ':') {
			len = len * 10 + ch - '0';
		}

		return len;
	}

	static String readString(InputStream in
		int len = readLength(in

		char[] chars = new char[len];

		for (int i = 0; i != chars.length; i++) {
			chars[i] = (char) in.read();
		}

		return new String(chars);
	}

	static byte[] readBytes(InputStream in
		int len = readLength(in

		byte[] data = new byte[len];

		Streams.readFully(in

		return data;
	}

	static S2K parseS2K(InputStream in) throws IOException {
		skipOpenParenthesis(in);

		readString(in
		byte[] iv = readBytes(in
		final long iterationCount = Long.parseLong(readString(in

		skipCloseParenthesis(in);

		S2K s2k = new S2K(HashAlgorithmTags.SHA1
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
