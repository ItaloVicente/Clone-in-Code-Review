
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;

public class RawTextIgnoreLeadingWhitespace extends RawText {

	public RawTextIgnoreLeadingWhitespace(byte[] input) {
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

		as = trimLeadingWhitespace(a.content
		bs = trimLeadingWhitespace(b.content

		if (ae - as != be - bs)
			return false;

		while (as < ae) {
			if (a.content[as++] != b.content[bs++])
				return false;
		}
		return true;
	}

	@Override
	protected int hashLine(final byte[] raw
		int hash = 5381;
		ptr = trimLeadingWhitespace(raw
		for (; ptr < end; ptr++) {
			hash = (hash << 5) ^ (raw[ptr] & 0xff);
		}
		return hash;
	}
}
