	Iterable<Integer> getRetrySleepMs() {
		return retrySleepMs;
	}

	void setRetrySleepMs(List<Integer> retrySleepMs) {
		if (retrySleepMs == null || retrySleepMs.isEmpty()
				|| retrySleepMs.get(0).intValue() != 0) {
			throw new IllegalArgumentException();
		}
		this.retrySleepMs = retrySleepMs;
	}

	static void sleep(long ms) throws InterruptedIOException {
		if (ms <= 0) {
			return;
		}
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			InterruptedIOException ie = new InterruptedIOException();
			ie.initCause(e);
			throw ie;
		}
	}

