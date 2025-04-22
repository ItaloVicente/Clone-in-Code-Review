	/**
	 * {@inheritDoc}
	 * <p>
	 * Sort packs according to the optimal lookup ordering.
	 * <p>
	 * This method tries to position packs in the order readers should examine
	 * them when looking for objects by SHA-1. The default tries to sort packs
	 * with more recent modification dates before older packs, and packs with
	 * fewer objects before packs with more objects.
	 */
	@Override
	public int compareTo(DfsPackDescription b) {
		PackSource as = getPackSource();
		PackSource bs = b.getPackSource();
		int cmp = PackSource.DEFAULT_COMPARATOR.compare(as, bs);
		if (cmp != 0) {
			return cmp;
		}

		if (as == bs && isGC(as)) {
			cmp = Long.signum(getFileSize(PACK) - b.getFileSize(PACK));
			if (cmp != 0) {
				return cmp;
			}
		}

		cmp = Long.signum(b.getLastModified() - getLastModified());
		if (cmp != 0)
			return cmp;

		return Long.signum(getObjectCount() - b.getObjectCount());
	}

