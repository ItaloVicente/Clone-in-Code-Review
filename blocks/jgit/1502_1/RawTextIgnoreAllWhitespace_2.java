
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.util.RawCharUtil.isWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;

public abstract class RawTextComparator extends DiffComparator<RawText> {
	public static final RawTextComparator DEFAULT = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

			if (a.hashes[ai] != b.hashes[bi])
				return false;

			int as = a.lines.get(ai);
			int bs = b.lines.get(bi);
			final int ae = a.lines.get(ai + 1);
			final int be = b.lines.get(bi + 1);

			if (ae - as != be - bs)
				return false;

			while (as < ae) {
				if (a.content[as++] != b.content[bs++])
					return false;
			}
			return true;
		}

		@Override
		public int hashLine(final byte[] raw
			int hash = 5381;
			for (; ptr < end; ptr++)
				hash = (hash << 5) ^ (raw[ptr] & 0xff);
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_ALL = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

			if (a.hashes[ai] != b.hashes[bi])
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

				while (as < ae - 1 && isWhitespace(ac)) {
					as++;
					ac = a.content[as];
				}

				while (bs < be - 1 && isWhitespace(bc)) {
					bs++;
					bc = b.content[bs];
				}

				if (ac != bc)
					return false;

				as++;
				bs++;
			}

			return as == ae && bs == be;
		}

		@Override
		public int hashLine(byte[] raw
			int hash = 5381;
			for (; ptr < end; ptr++) {
				byte c = raw[ptr];
				if (!isWhitespace(c))
					hash = (hash << 5) ^ (c & 0xff);
			}
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_LEADING = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

			if (a.hashes[ai] != b.hashes[bi])
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
		public int hashLine(final byte[] raw
			int hash = 5381;
			ptr = trimLeadingWhitespace(raw
			for (; ptr < end; ptr++) {
				hash = (hash << 5) ^ (raw[ptr] & 0xff);
			}
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_TRAILING = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

			if (a.hashes[ai] != b.hashes[bi])
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
		public int hashLine(final byte[] raw
			int hash = 5381;
			end = trimTrailingWhitespace(raw
			for (; ptr < end; ptr++) {
				hash = (hash << 5) ^ (raw[ptr] & 0xff);
			}
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_CHANGE = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

			if (a.hashes[ai] != b.hashes[bi])
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
		public int hashLine(final byte[] raw
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
	};

	@Override
	public int size(RawText seq) {
		return seq.size();
	}

	@Override
	public int hash(RawText seq
		return seq.hashes[ptr + 1];
	}

	public abstract int hashLine(byte[] raw
}
