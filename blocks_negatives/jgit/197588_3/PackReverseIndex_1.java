	/**
	 * Create reverse index from straight/forward pack index, by indexing all
	 * its entries.
	 *
	 * @param packIndex
	 *            forward index - entries to (reverse) index.
	 */
	public PackReverseIndex(PackIndex packIndex) {
		index = packIndex;

		final long cnt = index.getObjectCount();
		if (cnt + 1 > Integer.MAX_VALUE)
			throw new IllegalArgumentException(
					JGitText.get().hugeIndexesAreNotSupportedByJgitYet);

		if (cnt == 0) {
			bucketSize = Long.MAX_VALUE;
			offsetIndex = new int[1];
			nth = new int[0];
			return;
		}

		final long[] offsetsBySha1 = new long[(int) cnt];

		long maxOffset = 0;
		int ith = 0;
		for (MutableEntry me : index) {
			final long o = me.getOffset();
			offsetsBySha1[ith++] = o;
			if (o > maxOffset)
				maxOffset = o;
		}

		bucketSize = maxOffset / cnt + 1;
		int[] bucketIndex = new int[(int) cnt];
		int[] bucketValues = new int[(int) cnt + 1];
		for (int oi = 0; oi < offsetsBySha1.length; oi++) {
			final long o = offsetsBySha1[oi];
			final int bucket = (int) (o / bucketSize);
			final int bucketValuesPos = oi + 1;
			final int current = bucketIndex[bucket];
			bucketIndex[bucket] = bucketValuesPos;
			bucketValues[bucketValuesPos] = current;
		}

		int nthByOffset = 0;
		nth = new int[offsetsBySha1.length];
		for (int bi = 0; bi < bucketIndex.length; bi++) {
			final int start = nthByOffset;
			for (int vi = bucketIndex[bi]; vi > 0; vi = bucketValues[vi]) {
				final int nthBySha1 = vi - 1;
				final long o = offsetsBySha1[nthBySha1];
				int insertion = nthByOffset++;
				for (; start < insertion; insertion--) {
					if (o > offsetsBySha1[nth[insertion - 1]])
						break;
					nth[insertion] = nth[insertion - 1];
				}
				nth[insertion] = nthBySha1;
			}
			offsetIndex[bi] = nthByOffset;
		}
	}

