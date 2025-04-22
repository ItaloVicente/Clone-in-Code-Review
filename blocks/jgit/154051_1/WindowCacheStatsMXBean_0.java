	default double getEvictionRatio() {
		long evictionCount = getEvictionCount();
		long requestCount = getRequestCount();
		return (requestCount == 0) ? 0.0
				: (double) evictionCount / requestCount;
	}

