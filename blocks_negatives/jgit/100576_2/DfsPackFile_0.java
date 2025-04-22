	/**
	 * File offset used to cache {@link #index} in {@link DfsBlockCache}.
	 * <p>
	 * To better manage memory, the forward index is stored as a single block in
	 * the block cache under this file position. A negative value is used
	 * because it cannot occur in a normal pack file, and it is less likely to
	 * collide with a valid data block from the file as the high bits will all
	 * be set when treated as an unsigned long by the cache code.
	 */
	private static final long POS_INDEX = -1;

	/** Offset used to cache {@link #reverseIndex}. See {@link #POS_INDEX}. */
	private static final long POS_REVERSE_INDEX = -2;

	/** Offset used to cache {@link #bitmapIndex}. See {@link #POS_INDEX}. */
	private static final long POS_BITMAP_INDEX = -3;

