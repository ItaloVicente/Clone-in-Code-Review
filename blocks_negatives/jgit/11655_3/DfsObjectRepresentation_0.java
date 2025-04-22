	final ObjectToPack object;

	DfsPackFile pack;

	/**
	 * Position of {@link #pack} in the reader's pack list. Lower numbers are
	 * newer/more recent packs and less likely to contain the best format for a
	 * base object. Higher numbered packs are bigger, more stable, and favored
	 * by PackWriter when selecting representations... but only if they come
	 * last in the representation ordering.
	 */
	int packIndex;

	long offset;

