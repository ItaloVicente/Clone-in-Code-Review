	/**
	 * Lock for initialization of {@link #index} and {@link #corruptObjects}.
	 * <p>
	 * This lock ensures only one thread can perform the initialization work.
	 */
	private final Object initLock = new Object();

