	/**
	 * Ratio of cache requests which were hits defined as
	 * {@code hitCount / requestCount}, or {@code 1.0} when
	 * {@code requestCount == 0}. Note that {@code hitRate + missRate =~ 1.0}.
	 *
	 * @return the ratio of cache requests which were hits
	 * @since 5.1.13
	 */
	public double hitRatio() {
		long requestCount = requestCount();
		return (requestCount == 0) ? 1.0 : (double) hitCount / requestCount;
	}

	/**
	 * Number of cache misses.
	 *
	 * @return number of cash misses
	 * @since 5.1.13
	 */
	public long missCount() {
