	/**
	 * (offset31, truly) Offsets accommodating in 31 bits.
	 */
	private final int offsets32[];

	/**
	 * Offsets not accommodating in 31 bits.
	 */
	private final long offsets64[];

	/** Position of the corresponding {@link #offsets32} in {@link #index}. */
	private final int nth32[];

	/** Position of the corresponding {@link #offsets64} in {@link #index}. */
	private final int nth64[];
