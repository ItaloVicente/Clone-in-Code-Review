
package org.eclipse.jgit.diff;

import java.util.Arrays;

import org.eclipse.jgit.util.LongList;

public class PatienceDiff<S extends Sequence> {
	private static final int HASH_SHIFT = 32;

	public static <S extends Sequence> EditList diff(SequenceComparator<S> cmp
			S a
		Edit e = new Edit(0
		e = cmp.reduceCommonStartEnd(a

		PatienceDiff<S> d = new PatienceDiff<S>(cmp
		d.diff(e);
		return d.edits;
	}

	private final SequenceComparator<S> cmp;

	private final S a;

	private final S b;

	private final EditList edits;

	private PatienceDiff(SequenceComparator<S> cmp
		this.cmp = cmp;
		this.a = a;
		this.b = b;
		this.edits = new EditList();
	}

	private void diff(Edit e) {
		switch (e.getType()) {
		case INSERT:
		case DELETE:
			edits.add(e);
			return;

		case REPLACE:
			break;

		case EMPTY:
			return;
		}

		LongList matchPoints = match(index(a
				e.beginB
		if (matchPoints.size() == 0) {
			edits.add(e);
			return;
		}

		Edit lcs = longestCommonSubsequence(e

		diff(e.before(lcs));
		diff(e.after(lcs));
	}

	private Edit longestCommonSubsequence(Edit e
		Edit lcs = new Edit(0
		for (int i = 0; i < matchPoints.size(); i++) {
			long rec = matchPoints.get(i);

			int bs = hashOf(rec);
			if (bs < lcs.endB)
				continue;

			int as = lineOf(rec);
			int ae = as + 1;
			int be = bs + 1;

			while (e.beginA < as && e.beginB < bs
					&& cmp.equals(a
				as--;
				bs--;
			}
			while (ae < e.endA && be < e.endB && cmp.equals(a
				ae++;
				be++;
			}

			if (lcs.getLengthB() < be - bs) {
				lcs.beginA = as;
				lcs.beginB = bs;
				lcs.endA = ae;
				lcs.endB = be;
			}
		}
		return lcs;
	}

	private LongList match(long[] ah
		final LongList matches = new LongList();

		int aIdx = 0;
		int bIdx = 0;
		int aKey = hashOf(ah[aIdx]);
		int bKey = hashOf(bh[bIdx]);

		for (;;) {
			if (aKey < bKey) {
				if (++aIdx == ah.length)
					break;
				aKey = hashOf(ah[aIdx]);

			} else if (aKey > bKey) {
				if (++bIdx == bh.length)
					break;
				bKey = hashOf(bh[bIdx]);

			} else if (!isUnique(a
				if (++aIdx == ah.length)
					break;
				aKey = hashOf(ah[aIdx]);

			} else if (!isUnique(b
				if (++bIdx == bh.length)
					break;
				bKey = hashOf(bh[bIdx]);

			} else {
				final int aLine = lineOf(ah[aIdx]);
				final int bLine = lineOf(bh[bIdx]);

				if (cmp.equals(a
					matches.add(pair(bLine

				if (++aIdx == ah.length || ++bIdx == bh.length)
					break;
				aKey = hashOf(ah[aIdx]);
				bKey = hashOf(bh[bIdx]);
			}
		}

		matches.sort();
		return matches;
	}

	private long[] index(S content
		long[] index = new long[ae - as];
		for (int i = 0; as < ae; as++
			index[i] = pair(cmp.hash(content
		Arrays.sort(index);
		return index;
	}

	private boolean isUnique(S raw
		long rec = hashes[ptr];
		final int hash = hashOf(rec);
		final int line = lineOf(rec);

		for (int i = ptr - 1; 0 <= i; i--) {
			rec = hashes[i];
			if (hashOf(rec) != hash)
				break;
			if (cmp.equals(raw
				return false;
		}

		while (++ptr < hashes.length) {
			rec = hashes[ptr];
			if (hashOf(rec) != hash)
				return true;
			if (cmp.equals(raw
				return false;
		}

		return true;
	}

	private static long pair(int hash
		return (((long) hash) << HASH_SHIFT) | line;
	}

	private static int hashOf(long v) {
		return (int) (v >>> HASH_SHIFT);
	}

	private static int lineOf(long v) {
		return (int) v;
	}
}
