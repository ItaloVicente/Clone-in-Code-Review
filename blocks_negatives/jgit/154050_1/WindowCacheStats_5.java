	/**
	 * Ratio of cache requests which were misses defined as
	 * {@code missCount / requestCount}, or {@code 0.0} when
	 * {@code requestCount == 0}. Note that {@code hitRate + missRate =~ 1.0}.
	 * Cache misses include all requests which weren't cache hits, including
	 * requests which resulted in either successful or failed loading attempts.
	 *
	 * @return the ratio of cache requests which were misses
	 * @since 5.1.13
	 */
	public double missRatio() {
		long requestCount = requestCount();
		return (requestCount == 0) ? 0.0 : (double) missCount / requestCount;
	}

	/**
	 * Number of successful loads
	 *
	 * @return number of successful loads
	 * @since 5.1.13
	 */
	public long loadSuccessCount() {
