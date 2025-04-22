	/**
	 * Force this object to be loaded into memory and pinned in this loader.
	 * <p>
	 * Once materialized, subsequent get operations for the following methods
	 * will always succeed without raising an exception, as all information is
	 * pinned in memory by this loader instance.
	 * <ul>
	 * <li>{@link #getType()}</li>
	 * <li>{@link #getSize()}</li>
	 * <li>{@link #getBytes()}, {@link #getCachedBytes}</li>
	 * </ul>
	 *
	 * @param curs
	 *            temporary thread storage during data access.
	 * @throws IOException
	 *             the object cannot be read.
	 */
	abstract void materialize(WindowCursor curs) throws IOException;

