package org.eclipse.jgit.storage.file;

public interface WindowCacheStatsMXBean {

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
	}

	default long getLoadCount() {
		return getLoadSuccessCount() + getLoadFailureCount();
	}

	long getEvictionCount();

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

}
