
package org.eclipse.jgit.diff;

import java.util.Arrays;

import org.eclipse.jgit.util.LongList;

public class PatienceDiff {
	private static final int HASH_SHIFT = 32;

	public static EditList diff(RawText a
		PatienceDiff d = new PatienceDiff(a
		d.diff(0
		return d.edits;
	}

	private final EditList edits;

	private final RawText a;

	private final RawText b;

	private PatienceDiff(RawText a
		this.edits = new EditList();
		this.a = a;
		this.b = b;
	}

	private void diff(int as
		while (as < ae && bs < be && a.equals(as
			as++;
			bs++;
		}
		if (as == ae && bs == be)
			return;

		while (as < ae && bs < be && a.equals(ae - 1
			ae--;
			be--;
		}

		if (as == ae || bs == be) {
			edits.add(new Edit(as
			return;
		}

		LongList matchPoints = match(index(a
		if (matchPoints.size() == 0) {
			edits.add(new Edit(as
			return;
		}

		int longest = 0;
		int l_as = 0
		for (int i = 0; i < matchPoints.size(); i++) {
			long rec = matchPoints.get(i);

			int m_bs = hashOf(rec);
			if (m_bs < l_be)
				continue;

			int m_as = lineOf(rec);
			int m_ae = m_as + 1;
			int m_be = m_bs + 1;

			while (as < m_as && bs < m_bs && a.equals(m_as - 1
				m_as--;
				m_bs--;
			}
			while (m_ae < ae && m_be < be && a.equals(m_ae
				m_ae++;
				m_be++;
			}

			if (longest < m_be - m_bs) {
				longest = m_be - m_bs;
				l_as = m_as;
				l_ae = m_ae;
				l_bs = m_bs;
				l_be = m_be;
			}
		}

		if (as < l_as || bs < l_bs)
			diff(as

		if (l_ae < ae || l_be < be)
			diff(l_ae
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

				if (a.equals(aLine
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

	private static long[] index(RawText a
		long[] index = new long[ae - as];
		int[] in = a.hashes;
		for (int i = 0; as < ae; as++
			index[i] = pair(in[as + 1]
		Arrays.sort(index);
		return index;
	}

	private static boolean isUnique(RawText raw
		long rec = hashes[ptr++];
		final int hash = hashOf(rec);
		final int line = lineOf(rec);
		while (ptr < hashes.length) {
			rec = hashes[ptr++];
			if (hashOf(rec) != hash)
				return true;
			if (raw.equals(line
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
