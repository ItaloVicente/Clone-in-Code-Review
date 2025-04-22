		return WindowCache.getInstance().getStats().getOpenByteCount();
	}

	public static WindowCacheStats getStats() {
		return WindowCache.getInstance().getStats();
	}

	long getHitCount();

	default double getHitRatio() {
		long requestCount = getRequestCount();
		return (requestCount == 0) ? 1.0
				: (double) getHitCount() / requestCount;
	}

	long getMissCount();

	default double getMissRatio() {
		long requestCount = getRequestCount();
		return (requestCount == 0) ? 0.0
				: (double) getMissCount() / requestCount;
	}

	long getLoadSuccessCount();

	long getLoadFailureCount();

	default double getLoadFailureRatio() {
		long loadFailureCount = getLoadFailureCount();
		long totalLoadCount = getLoadSuccessCount() + loadFailureCount;
		return (totalLoadCount == 0) ? 0.0
				: (double) loadFailureCount / totalLoadCount;
