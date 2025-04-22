	/**
	 * Obtain the available cached packs.
	 * <p>
	 * A cached pack has known starting points and may be sent entirely as-is,
	 * with almost no effort on the sender's part.
	 *
	 * @return the available cached packs.
	 * @throws IOException
	 *             the cached packs cannot be listed from the repository.
	 *             Callers may choose to ignore this and continue as-if there
	 *             were no cached packs.
	 */
	public Collection<CachedPack> getCachedPacks() throws IOException;

