
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.util.RawCharUtil.isWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;

import org.eclipse.jgit.util.IntList;

public abstract class RawTextComparator extends SequenceComparator<RawText> {
	public static final RawTextComparator DEFAULT = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

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
		protected int hashRegion(byte[] raw
			int hash = 5381;
			for (; ptr < end; ptr++)
				hash = ((hash << 5) + hash) + (raw[ptr] & 0xff);
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_ALL = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

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
		protected int hashRegion(byte[] raw
			int hash = 5381;
			for (; ptr < end; ptr++) {
				byte c = raw[ptr];
				if (!isWhitespace(c))
					hash = ((hash << 5) + hash) + (c & 0xff);
			}
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_LEADING = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

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
		protected int hashRegion(byte[] raw
			int hash = 5381;
			ptr = trimLeadingWhitespace(raw
			for (; ptr < end; ptr++)
				hash = ((hash << 5) + hash) + (raw[ptr] & 0xff);
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_TRAILING = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

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
		protected int hashRegion(byte[] raw
			int hash = 5381;
			end = trimTrailingWhitespace(raw
			for (; ptr < end; ptr++)
				hash = ((hash << 5) + hash) + (raw[ptr] & 0xff);
			return hash;
		}
	};

	public static final RawTextComparator WS_IGNORE_CHANGE = new RawTextComparator() {
		@Override
		public boolean equals(RawText a
			ai++;
			bi++;

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
		protected int hashRegion(byte[] raw
			int hash = 5381;
			end = trimTrailingWhitespace(raw
			while (ptr < end) {
				byte c = raw[ptr];
				hash = ((hash << 5) + hash) + (c & 0xff);
				if (isWhitespace(c))
					ptr = trimLeadingWhitespace(raw
				else
					ptr++;
			}
			return hash;
		}
	};

	@Override
	public int hash(RawText seq
		final int begin = seq.lines.get(lno + 1);
		final int end = seq.lines.get(lno + 2);
		return hashRegion(seq.content
	}

	@Override
	public Edit reduceCommonStartEnd(RawText a

		if (e.beginA == e.endA || e.beginB == e.endB)
			return e;

		byte[] aRaw = a.content;
		byte[] bRaw = b.content;

		int aPtr = a.lines.get(e.beginA + 1);
		int bPtr = a.lines.get(e.beginB + 1);

		int aEnd = a.lines.get(e.endA + 1);
		int bEnd = b.lines.get(e.endB + 1);

		if (aPtr < 0 || bPtr < 0 || aEnd > aRaw.length || bEnd > bRaw.length)
			throw new ArrayIndexOutOfBoundsException();

		while (aPtr < aEnd && bPtr < bEnd && aRaw[aPtr] == bRaw[bPtr]) {
			aPtr++;
			bPtr++;
		}

		while (aPtr < aEnd && bPtr < bEnd && aRaw[aEnd - 1] == bRaw[bEnd - 1]) {
			aEnd--;
			bEnd--;
		}

		e.beginA = findForwardLine(a.lines
		e.beginB = findForwardLine(b.lines

		e.endA = findReverseLine(a.lines

		final boolean partialA = aEnd < a.lines.get(e.endA + 1);
		if (partialA)
			bEnd += a.lines.get(e.endA + 1) - aEnd;

		e.endB = findReverseLine(b.lines

		if (!partialA && bEnd < b.lines.get(e.endB + 1))
			e.endA++;

		return super.reduceCommonStartEnd(a
	}

	private static int findForwardLine(IntList lines
		final int end = lines.size() - 2;
		while (idx < end && lines.get(idx + 2) < ptr)
			idx++;
		return idx;
	}

	private static int findReverseLine(IntList lines
		while (0 < idx && ptr <= lines.get(idx))
			idx--;
		return idx;
	}

	protected abstract int hashRegion(byte[] raw
}
