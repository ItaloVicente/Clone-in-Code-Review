	private boolean shouldIncludeEntry(TreeWalk tw) {
		final int mHead = tw.getRawMode(BASE_NTH);
		final int mCache = tw.getRawMode(REMOTE_NTH);

				|| (mHead != mCache || (mCache != TREE.getBits() && !tw
						.idEqual(BASE_NTH, REMOTE_NTH))); // modified
	}

