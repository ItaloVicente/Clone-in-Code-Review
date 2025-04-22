	int incrementTransientErrorCount() {
		return transientErrorCount.incrementAndGet();
	}

	void resetTransientErrorCount() {
		transientErrorCount.set(0);
	}

