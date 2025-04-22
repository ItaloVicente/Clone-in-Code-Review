
package org.eclipse.jgit.diff;

import org.eclipse.jgit.util.RawCharUtil;

public class RawTextIgnoreAllWhitespace extends RawText {

	public RawTextIgnoreAllWhitespace(byte[] input) {
		super(input);
	}

	@Override
	public boolean equals(final int i
		return equals(this
	}

	private static boolean equals(final RawText a
			final RawText b
		if (a.hashes.get(ai) != b.hashes.get(bi)) {
			return false;
		}

		int as = a.lines.get(ai);
		int bs = b.lines.get(bi);
		final int ae = a.lines.get(ai + 1);
		final int be = b.lines.get(bi + 1);

		while (as < ae && bs < be) {
			while (as < ae - 1 && RawCharUtil.isWhitespace(a.content[as])) {
				as++;
			}

			while (bs < be - 1 && RawCharUtil.isWhitespace(b.content[bs])) {
				bs++;
			}

			if (a.content[as] != b.content[bs]) {
				return false;
			}

			as++;
			bs++;
		}

		while (as < ae) {
			if (!RawCharUtil.isWhitespace(a.content[as++])) {
				return false;
			}
		}

		while (bs < be) {
			if (!RawCharUtil.isWhitespace(b.content[bs++])) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected int hashLine(final byte[] raw
		int hash = 5381;
		for (; ptr < end; ptr++) {
			if (!RawCharUtil.isWhitespace(raw[ptr])) {
				hash = (hash << 5) ^ (raw[ptr] & 0xff);
			}
		}
		return hash;
	}
}
