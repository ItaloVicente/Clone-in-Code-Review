	/** The {@link #idHash} table will not grow bigger than this, ever. */
	private static final int MAX_HASH_SIZE = 1 << MAX_HASH_BITS;

	/** Prime just before {@link #MAX_HASH_SIZE}. */
	private static final int P = 131071;

