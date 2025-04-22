	/**
	 * Index elements in sequence B for later matching with sequence A.
	 *
	 * This is the first stage of preparing an index to find the longest common
	 * sequence. Elements of sequence B in the range [ptr, end) are scanned in
	 * order and added to the internal hashtable.
	 *
	 * If prior matches were given in the constructor, these may be used to
	 * fast-forward through sections of B to avoid unnecessary recomputation.
	 */
	private void scanB() {
		int ptr = region.beginB;
		final int end = region.endB;
		int pIdx = pBegin;
		SCAN: while (ptr < end) {
			final int tIdx = cmp.hash(b, ptr) & tableMask;

			if (pIdx < pEnd) {
				final long priorRec = pCommon[pIdx];
				if (ptr == bOf(priorRec)) {
					insertB(tIdx, ptr);
					pIdx++;
					ptr = aOfRaw(priorRec);
					continue SCAN;
				}
			}

			for (int eIdx = table[tIdx]; eIdx != 0; eIdx = next[eIdx]) {
				final long rec = ptrs[eIdx];
				if (cmp.equals(b, ptr, b, bOf(rec))) {
					ptrs[eIdx] = rec | B_DUPLICATE;
					ptr++;
					continue SCAN;
				}
			}

			insertB(tIdx, ptr);
			ptr++;
		}
	}

	private void insertB(final int tIdx, int ptr) {
		final int eIdx = ++entryCnt;
		ptrs[eIdx] = ((long) ptr) << B_SHIFT;
		next[eIdx] = table[tIdx];
		table[tIdx] = eIdx;
	}

	/**
	 * Index elements in sequence A for later matching.
	 *
	 * This is the second stage of preparing an index to find the longest common
	 * sequence. The state requires {@link #scanB()} to have been invoked first.
	 *
	 * Each element of A in the range [ptr, end) are searched for in the
	 * internal hashtable, to see if B has already registered a location.
	 *
	 * If prior matches were given in the constructor, these may be used to
	 * fast-forward through sections of A to avoid unnecessary recomputation.
	 */
