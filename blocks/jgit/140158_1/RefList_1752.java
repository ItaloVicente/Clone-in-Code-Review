
package org.eclipse.jgit.util;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;

public class RawSubStringPattern {
	private final String needleString;

	private final byte[] needle;

	public RawSubStringPattern(String patternText) {
		if (patternText.length() == 0)
			throw new IllegalArgumentException(JGitText.get().cannotMatchOnEmptyString);
		needleString = patternText;

		final byte[] b = Constants.encode(patternText);
		needle = new byte[b.length];
		for (int i = 0; i < b.length; i++)
			needle[i] = lc(b[i]);
	}

	public int match(RawCharSequence rcs) {
		final int needleLen = needle.length;
		final byte first = needle[0];

		final byte[] text = rcs.buffer;
		int matchPos = rcs.startPtr;
		final int maxPos = rcs.endPtr - needleLen;

		OUTER: for (; matchPos <= maxPos; matchPos++) {
			if (neq(first
				while (++matchPos <= maxPos && neq(first
				}
				if (matchPos > maxPos)
					return -1;
			}

			int si = matchPos + 1;
			for (int j = 1; j < needleLen; j++
				if (neq(needle[j]
					continue OUTER;
			}
			return matchPos;
		}
		return -1;
	}

	private static final boolean neq(byte a
		return a != b && a != lc(b);
	}

	private static final byte lc(byte q) {
		return (byte) StringUtils.toLowerCase((char) (q & 0xff));
	}

	public String pattern() {
		return needleString;
	}

	@Override
	public String toString() {
		return pattern();
	}
}
