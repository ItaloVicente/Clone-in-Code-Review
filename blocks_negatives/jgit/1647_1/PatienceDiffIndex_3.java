	/**
	 * Pairs of beginB, endB indices found to be common and unique.
	 *
	 * In order to find the longest common (but unique) sequence within a
	 * region, we also found all of the other common but unique sequences in
	 * that same region. This array stores all of those results, allowing them
	 * to be passed into the subsequent recursive passes so we can later reuse
	 * these matches and avoid recomputing the same points again.
	 */
	long[] nCommon;

	/** Number of items in {@link #nCommon}. */
	int nCnt;

	/** Index of the longest common subsequence in {@link #nCommon}. */
	int cIdx;

	PatienceDiffIndex(HashedSequenceComparator<S> cmp, //
			HashedSequence<S> a, //
			HashedSequence<S> b, //
			Edit region, //
			long[] pCommon, int pIdx, int pCnt) {
