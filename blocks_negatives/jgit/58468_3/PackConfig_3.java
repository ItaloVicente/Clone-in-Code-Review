	/**
	 * Get the range of commits for which to build bitmaps. The range starts
	 * from the most recent commit.
	 *
	 * A value of 0 creates bitmaps for only branch tips. A value of -1 creates
	 * bitmaps spaced through the entire history of commits.
	 *
	 * Default setting: {@value #DEFAULT_BITMAP_COMMIT_RANGE}
	 *
	 * @return the range of commits for which to create bitmaps, starting with
	 *         the most recent commit
	 * @see PackIndexWriter
	 * @since 4.2
	 */
	public int getBitmapCommitRange() {
		return bitmapCommitRange;
	}

	/**
	 * Set the range of commits for which to build bitmaps.
	 *
	 * Default setting: {@value #DEFAULT_BITMAP_COMMIT_RANGE}
	 *
	 * @param range
	 *            the range of commits for which to create bitmaps, starting
	 *            with the most recent commit
	 * @see PackIndexWriter
	 * @since 4.2
	 */
	public void setBitmapCommitRange(final int range) {
		bitmapCommitRange = range;
	}

