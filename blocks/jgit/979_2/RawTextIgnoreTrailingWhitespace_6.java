
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;

public class RawTextIgnoreTrailingWhitespace extends RawText {

	public RawTextIgnoreTrailingWhitespace(byte[] input) {
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
		end = trimTrailingWhitespace(raw
		for (; ptr < end; ptr++) {
			hash = (hash << 5) ^ (raw[ptr] & 0xff);
		}
		return hash;
	}
}
