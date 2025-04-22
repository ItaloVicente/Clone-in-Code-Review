
	default long getLoadCount() {
		return getLoadSuccessCount() + getLoadFailureCount();
	}

	long getEvictionCount();

	default double getEvictionRatio() {
		long evictionCount = getEvictionCount();
		long requestCount = getRequestCount();
		return (requestCount == 0) ? 0.0
				: (double) evictionCount / requestCount;
	}

	default long getRequestCount() {
		return getHitCount() + getMissCount();
	}

	default double getAverageLoadTime() {
		long totalLoadCount = getLoadSuccessCount() + getLoadFailureCount();
		return (totalLoadCount == 0) ? 0.0
				: (double) getTotalLoadTime() / totalLoadCount;
	}

	long getTotalLoadTime();

	long getOpenFileCount();

	long getOpenByteCount();

	void resetCounters();
