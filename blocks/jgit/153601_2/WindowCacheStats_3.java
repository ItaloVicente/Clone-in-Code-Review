		return WindowCache.getInstance().getStats().openByteCount();
	}

	public static WindowCacheStats getStats() {
		return WindowCache.getInstance().getStats();
	}

	private final long hitCount;
	private final long missCount;
	private final long loadSuccessCount;
	private final long loadFailureCount;
	private final long totalLoadTime;
	private final long evictionCount;
	private final long openFileCount;
	private final long openByteCount;

	public WindowCacheStats(long hitCount
			long loadSuccessCount
			long evictionCount
		this.hitCount = hitCount;
		this.missCount = missCount;
		this.loadSuccessCount = loadSuccessCount;
		this.loadFailureCount = loadFailureCount;
		this.totalLoadTime = totalLoadTime;
		this.evictionCount = evictionCount;
		this.openFileCount = openFileCount;
		this.openByteCount = openByteCount;
	}

	public long hitCount() {
		return hitCount;
	}

	public double hitRate() {
		long requestCount = requestCount();
		return (requestCount == 0) ? 1.0 : (double) hitCount / requestCount;
	}

	public long missCount() {
		return missCount;
	}

	public double missRate() {
		long requestCount = requestCount();
		return (requestCount == 0) ? 0.0 : (double) missCount / requestCount;
	}

	public long loadSuccessCount() {
		return loadSuccessCount;
	}

	public long loadFailureCount() {
		return loadFailureCount;
	}

	public double loadFailureRate() {
		long totalLoadCount = loadSuccessCount + loadFailureCount;
		return (totalLoadCount == 0) ? 0.0
				: (double) loadFailureCount / totalLoadCount;
	}

	public long loadCount() {
		return loadSuccessCount + loadFailureCount;
	}

	public long evictionCount() {
		return evictionCount;
	}

	public long requestCount() {
		return hitCount + missCount;
	}

	public double averageLoadTime() {
		long totalLoadCount = loadSuccessCount + loadFailureCount;
		return (totalLoadCount == 0) ? 0.0
				: (double) totalLoadTime / totalLoadCount;
	}

	public long totalLoadTime() {
		return totalLoadTime;
	}

	public long openFileCount() {
		return openFileCount;
	}

	public long openByteCount() {
		return openByteCount;
