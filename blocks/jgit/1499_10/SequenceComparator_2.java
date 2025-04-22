
package org.eclipse.jgit.diff;

final class PatienceDiffIndex<S extends Sequence> {
	private static final int A_DUPLICATE = 1;

	private static final int B_DUPLICATE = 2;

	private static final int DUPLICATE_MASK = B_DUPLICATE | A_DUPLICATE;

	private static final int A_SHIFT = 2;

	private static final int B_SHIFT = 31 + 2;

	private static final int PTR_MASK = 0x7fffffff;

	private final HashedSequenceComparator<S> cmp;

	private final HashedSequence<S> a;

	private final HashedSequence<S> b;

	private final Edit region;

	private final long[] pCommon;

	private final int pBegin;

	private final int pEnd;

	private final int[] table;

	private final int tableMask;


	private final int[] hash;

	private final long[] ptrs;

	private final int[] next;

	private int entryCnt;

	private int uniqueCommonCnt;

	long[] nCommon;

	int nCnt;

	int cIdx;

	PatienceDiffIndex(HashedSequenceComparator<S> cmp
			HashedSequence<S> a
			HashedSequence<S> b
			Edit region
			long[] pCommon
		this.cmp = cmp;
		this.a = a;
		this.b = b;
		this.region = region;
		this.pCommon = pCommon;
		this.pBegin = pIdx;
		this.pEnd = pCnt;

		final int blockCnt = region.getLengthB();
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

	private void scanB() {
		int ptr = region.beginB;
		final int end = region.endB;
		int pIdx = pBegin;
		SCAN: while (ptr < end) {
			final int key = cmp.hash(b
			final int tIdx = key & tableMask;

			if (pIdx < pEnd) {
				final long priorRec = pCommon[pIdx];
				if (ptr == bOf(priorRec)) {
					insertB(key
					pIdx++;
					ptr = aOfRaw(priorRec);
					continue SCAN;
				}
			}

			for (int eIdx = table[tIdx]; eIdx != 0; eIdx = next[eIdx]) {
				if (hash[eIdx] != key)
					continue;

				final long rec = ptrs[eIdx];
				if (cmp.equals(b
					ptrs[eIdx] = rec | B_DUPLICATE;
					ptr++;
					continue SCAN;
				}
			}

			insertB(key
			ptr++;
		}
	}

	private void insertB(final int key
		final int eIdx = ++entryCnt;
		hash[eIdx] = key;
		ptrs[eIdx] = ((long) ptr) << B_SHIFT;
		next[eIdx] = table[tIdx];
		table[tIdx] = eIdx;
	}

	private void scanA() {
		int ptr = region.beginA;
		final int end = region.endA;
		int pLast = pBegin - 1;
		SCAN: while (ptr < end) {
			final int key = cmp.hash(a
			final int tIdx = key & tableMask;

			for (int eIdx = table[tIdx]; eIdx != 0; eIdx = next[eIdx]) {
				final long rec = ptrs[eIdx];

				if (isDuplicate(rec) || hash[eIdx] != key)
					continue;

				final int aPtr = aOfRaw(rec);
				if (aPtr != 0 && cmp.equals(a
					ptrs[eIdx] = rec | A_DUPLICATE;
					uniqueCommonCnt--;
					ptr++;
					continue SCAN;
				}

				final int bs = bOf(rec);
				if (!cmp.equals(a
					ptr++;
					continue SCAN;
				}

				ptrs[eIdx] = rec | (((long) (ptr + 1)) << A_SHIFT);
				uniqueCommonCnt++;

				if (pBegin < pEnd) {
					for (int pIdx = pLast + 1;; pIdx++) {
						if (pIdx == pEnd)
							pIdx = pBegin;
						else if (pIdx == pLast)
							break;

						final long priorRec = pCommon[pIdx];
						final int priorB = bOf(priorRec);
						if (bs < priorB)
							break;
						if (bs == priorB) {
							ptr += aOfRaw(priorRec) - priorB;
							pLast = pIdx;
							continue SCAN;
						}
					}
				}

				ptr++;
				continue SCAN;
			}

			ptr++;
		}
	}

	Edit findLongestCommonSequence() {
		scanB();
		scanA();

		if (uniqueCommonCnt == 0)
			return null;

		nCommon = new long[uniqueCommonCnt];
		int pIdx = pBegin;
		Edit lcs = new Edit(0

		MATCH: for (int eIdx = 1; eIdx <= entryCnt; eIdx++) {
			final long rec = ptrs[eIdx];
			if (isDuplicate(rec) || aOfRaw(rec) == 0)
				continue;

			int bs = bOf(rec);
			if (bs < lcs.endB)
				continue;

			int as = aOf(rec);
			if (pIdx < pEnd) {
				final long priorRec = pCommon[pIdx];
				if (bs == bOf(priorRec)) {
					int be = aOfRaw(priorRec);

					if (lcs.getLengthB() < be - bs) {
						as -= bOf(rec) - bs;
						lcs.beginA = as;
						lcs.beginB = bs;
						lcs.endA = as + (be - bs);
						lcs.endB = be;
						cIdx = nCnt;
					}

					nCommon[nCnt] = priorRec;
					if (++nCnt == uniqueCommonCnt)
						break MATCH;

					pIdx++;
					continue MATCH;
				}
			}

			int ae = as + 1;
			int be = bs + 1;

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

			nCommon[nCnt] = (((long) bs) << B_SHIFT) | (((long) be) << A_SHIFT);
			if (++nCnt == uniqueCommonCnt)
				break MATCH;
		}

		return lcs;
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
