
package org.eclipse.jgit.util;

public class RawCharUtil {
	private static final boolean[] WHITESPACE = new boolean[256];

	static {
		WHITESPACE['\r'] = true;
		WHITESPACE['\n'] = true;
		WHITESPACE['\t'] = true;
		WHITESPACE[' '] = true;
	}

	public static boolean isWhitespace(byte c) {
		return WHITESPACE[c & 0xff];
	}

	public static int trimTrailingWhitespace(byte[] raw
		int ptr = end - 1;
		while (start <= ptr && isWhitespace(raw[ptr]))
			ptr--;

		return ptr + 1;
	}

	public static int trimLeadingWhitespace(byte[] raw
		while (start < end && isWhitespace(raw[start]))
			start++;

		return start;
	}

	private RawCharUtil() {
	}
}
