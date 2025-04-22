	 * @param packedGitLimit
	 *            maximum number of bytes to hold within this instance.
	 * @param packedGitWindowSize
	 *            number of bytes per window within the cache.
	 * @param packedGitMMAP
	 *            true to enable use of mmap when creating windows.
	 * @param deltaBaseCacheLimit
	 *            number of bytes to hold in the delta base cache.
	 * @deprecated Use {@link WindowCacheConfig} instead.
	 */
	public static void reconfigure(final int packedGitLimit,
			final int packedGitWindowSize, final boolean packedGitMMAP,
			final int deltaBaseCacheLimit) {
		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(packedGitLimit);
		c.setPackedGitWindowSize(packedGitWindowSize);
		c.setPackedGitMMAP(packedGitMMAP);
		c.setDeltaBaseCacheLimit(deltaBaseCacheLimit);
		reconfigure(c);
	}

	/**
	 * Modify the configuration of the window cache.
	 * <p>
	 * The new configuration is applied immediately. If the new limits are
	 * smaller than what what is currently cached, older entries will be purged
	 * as soon as possible to allow the cache to meet the new limit.
	 *
