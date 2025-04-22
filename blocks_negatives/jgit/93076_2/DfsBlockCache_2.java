	/**
	 * Modify the configuration of the window cache.
	 * <p>
	 * The new configuration is applied immediately, and the existing cache is
	 * cleared.
	 *
	 * @param cfg
	 *            the new window cache configuration.
	 * @throws IllegalArgumentException
	 *             the cache configuration contains one or more invalid
	 *             settings, usually too low of a limit.
	 */
	public static void reconfigure(DfsBlockCacheConfig cfg) {
		DfsBlockCache nc = new DfsBlockCache(cfg);
		DfsBlockCache oc = cache;
		cache = nc;
