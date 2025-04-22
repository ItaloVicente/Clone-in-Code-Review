	public MonotonicClock getClock() {
		return clock;
	}

	public Duration getMaxWaitForMonotonicClock() {
		return Duration.ofSeconds(5);
	}

	public boolean requireMonotonicLeaderElections() {
		return false;
	}

