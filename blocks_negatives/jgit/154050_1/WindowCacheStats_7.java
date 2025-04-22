	/**
	 * Ratio of cache load attempts which threw exceptions. This is defined as
	 * {@code loadFailureCount / (loadSuccessCount + loadFailureCount)}, or
	 * {@code 0.0} when {@code loadSuccessCount + loadFailureCount == 0}.
	 *
	 * @return the ratio of cache loading attempts which threw exceptions
	 * @since 5.1.13
	 */
	public double loadFailureRatio() {
		long totalLoadCount = loadSuccessCount + loadFailureCount;
		return (totalLoadCount == 0) ? 0.0
				: (double) loadFailureCount / totalLoadCount;
	}

	/**
	 * Total number of times that the cache attempted to load new values. This
	 * includes both successful load operations, as well as failed loads. This
	 * is defined as {@code loadSuccessCount + loadFailureCount}.
	 *
	 * @return the {@code loadSuccessCount + loadFailureCount}
	 * @since 5.1.13
	 */
	public long loadCount() {
		return loadSuccessCount + loadFailureCount;
	}

	/**
	 * Number of cache evictions
	 *
	 * @return number of evictions
	 * @since 5.1.13
	 */
	public long evictionCount() {
