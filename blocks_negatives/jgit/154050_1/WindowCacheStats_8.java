	/**
	 * Number of times the cache returned either a cached or uncached value.
	 * This is defined as {@code hitCount + missCount}.
	 *
	 * @return the {@code hitCount + missCount}
	 * @since 5.1.13
	 */
	public long requestCount() {
		return hitCount + missCount;
	}

	/**
	 * Average time in nanoseconds for loading new values. This is
	 * {@code totalLoadTime / (loadSuccessCount + loadFailureCount)}.
	 *
	 * @return the average time spent loading new values
	 * @since 5.1.13
	 */
	public double averageLoadTime() {
		long totalLoadCount = loadSuccessCount + loadFailureCount;
		return (totalLoadCount == 0) ? 0.0
				: (double) totalLoadTime / totalLoadCount;
	}

	/**
	 * Total time in nanoseconds the cache spent loading new values.
	 *
	 * @return the total number of nanoseconds the cache has spent loading new
	 *         values
	 * @since 5.1.13
	 */
	public long totalLoadTime() {
