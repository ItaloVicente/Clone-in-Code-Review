
package org.eclipse.jgit.diff;

final class PatienceDiffIndex<S extends Sequence> {
	private static final int A_DUPLICATE = 1;

	private static final int B_DUPLICATE = 2;

	private static final int DUPLICATE_MASK = B_DUPLICATE | A_DUPLICATE;

	private static final int A_SHIFT = 2;

	private static final int B_SHIFT = 31 + 2;

	private static final int PTR_MASK = 0x7fffffff;

	private final SequenceComparator<S> cmp;

	private final int[] table;

	private final int tableMask;


	private final int[] hash;

	private final long[] ptrs;

	private final int[] next;

	private int entryCnt;

	long[] nCommon;

	int nCnt;

	int cIdx;

	PatienceDiffIndex(SequenceComparator<S> cmp
		this.cmp = cmp;

		final int blockCnt = Math.max(region.getLengthA()
		if (blockCnt < 1) {
			table = new int[] {};
			tableMask = 0;

			hash = new int[] {};
			ptrs = new long[] {};
			next = new int[] {};

		} else {
			table = new int[tableSize(blockCnt)];
			tableMask = table.length - 1;

			hash = new int[1 + blockCnt];
			ptrs = new long[hash.length];
			next = new int[hash.length];
		}
	}

	void scanB(S seq
		SCAN: for (; ptr < end; ptr++) {
			final int key = cmp.hash(seq
			final int tIdx = key & tableMask;

			for (int eIdx = table[tIdx]; eIdx != 0; eIdx = next[eIdx]) {
				if (hash[eIdx] != key)
					continue;

				final long rec = ptrs[eIdx];
				if (cmp.equals(seq
					ptrs[eIdx] = rec | B_DUPLICATE;
					continue SCAN;
				}
			}

			final int eIdx = ++entryCnt;
			hash[eIdx] = key;
			ptrs[eIdx] = ((long) ptr) << B_SHIFT;
			next[eIdx] = table[tIdx];
			table[tIdx] = eIdx;
		}
	}

	void scanA(S a
		SCAN: for (; ptr < end; ptr++) {
			final int key = cmp.hash(a
			final int tIdx = key & tableMask;

			for (int eIdx = table[tIdx]; eIdx != 0; eIdx = next[eIdx]) {
				final long rec = ptrs[eIdx];

				if (isDuplicate(rec) || hash[eIdx] != key)
					continue;

				final int aPtr = aOfRaw(rec);
				if (aPtr != 0 && cmp.equals(a
					ptrs[eIdx] = rec | A_DUPLICATE;
					continue SCAN;
				}

				if (cmp.equals(a
					ptrs[eIdx] = rec | (((long) (ptr + 1)) << A_SHIFT);
					continue SCAN;
				}
			}
		}
	}

	Edit match(Edit region

		nCommon = new long[entryCnt];
		Edit lcs = new Edit(0

		MATCH: for (int eIdx = 1; eIdx <= entryCnt; eIdx++) {
			final long rec = ptrs[eIdx];
			if (isDuplicate(rec) || aOfRaw(rec) == 0)
				continue;

			int bs = bOf(rec);
			if (bs < lcs.endB)
				continue;

			int as = aOf(rec);
			int be;

			while (pIdx < pCnt) {
				final long p = pCommon[pIdx];
				if (bs < bOf(p))
					break;

				be = aOfRaw(p);
				if (be <= bs) {
					pIdx++;
					continue;
				}
				bs = bOf(p);

				if (lcs.getLengthB() < be - bs) {
					as -= bOf(rec) - bs;
					lcs.beginA = as;
					lcs.beginB = bs;
					lcs.endA = as + (be - bs);
					lcs.endB = be;
					cIdx = nCnt;
				}

				nCommon[nCnt++] = rec;
				pIdx++;
				continue MATCH;
			}

			int ae = as + 1;
			be = bs + 1;

			while (region.beginA < as && region.beginB < bs
					&& cmp.equals(a
				as--;
				bs--;
			}
			while (ae < region.endA && be < region.endB
					&& cmp.equals(a
				ae++;
				be++;
			}

			if (lcs.getLengthB() < be - bs) {
				lcs.beginA = as;
				lcs.beginB = bs;
				lcs.endA = ae;
				lcs.endB = be;
				cIdx = nCnt;
			}

			nCommon[nCnt++] = (((long) bs) << B_SHIFT)
					| (((long) be) << A_SHIFT);
		}

		return 0 < nCnt ? lcs : null;
	}

	private static boolean isDuplicate(long rec) {
		return (((int) rec) & DUPLICATE_MASK) != 0;
	}

	private static int aOfRaw(long rec) {
		return ((int) (rec >>> A_SHIFT)) & PTR_MASK;
	}

	private static int aOf(long rec) {
		return aOfRaw(rec) - 1;
	}

	private static int bOf(long rec) {
		return (int) (rec >>> B_SHIFT);
	}

	private static int tableSize(final int worstCaseBlockCnt) {
		int shift = 32 - Integer.numberOfLeadingZeros(worstCaseBlockCnt);
		int sz = 1 << (shift - 1);
		if (sz < worstCaseBlockCnt)
			sz <<= 1;
		return sz;
	}
}
