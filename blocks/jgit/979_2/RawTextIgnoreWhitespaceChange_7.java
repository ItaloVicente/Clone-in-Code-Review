
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.util.RawCharUtil.isWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;

public class RawTextIgnoreWhitespaceChange extends RawText {

	public RawTextIgnoreWhitespaceChange(byte[] input) {
		super(input);
	}

	@Override
	public boolean equals(final int i
		return equals(this
	}

	private static boolean equals(final RawText a
			final RawText b
		if (a.hashes.get(ai) != b.hashes.get(bi))
			return false;

		int as = a.lines.get(ai);
		int bs = b.lines.get(bi);
		int ae = a.lines.get(ai + 1);
		int be = b.lines.get(bi + 1);

		ae = trimTrailingWhitespace(a.content
		be = trimTrailingWhitespace(b.content

		while (as < ae && bs < be) {
			byte ac = a.content[as];
			byte bc = b.content[bs];

			if (ac != bc)
				return false;

			if (isWhitespace(ac))
				as = trimLeadingWhitespace(a.content
			else
				as++;

			if (isWhitespace(bc))
				bs = trimLeadingWhitespace(b.content
			else
				bs++;
		}
		return as == ae && bs == be;
	}

	@Override
	protected int hashLine(final byte[] raw
		int hash = 5381;
		end = trimTrailingWhitespace(raw
		while (ptr < end) {
			byte c = raw[ptr];
			hash = (hash << 5) ^ (c & 0xff);
			if (isWhitespace(c))
				ptr = trimLeadingWhitespace(raw
			else
				ptr++;
		}
		return hash;
	}
}
