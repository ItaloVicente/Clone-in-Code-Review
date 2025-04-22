	int incrementTransientErrorCount() {
		return ++transientErrorCount;
	}

	void resetTransientErrorCount() {
		transientErrorCount = 0;
	}

