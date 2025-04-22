	public MonotonicClock getClock() {
		return clock;
	}

	public long getMaxWaitForMonotonicClock(TimeUnit unit) {
		return unit.convert(5
	}

	public boolean requireMonotonicLeaderElections() {
		return false;
	}

