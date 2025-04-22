        @Override
        public void timeOut() {
            timedout = true;
        }

        @Override
        public boolean isTimedOut() {
            return timedout;
        }

	@Override
	public boolean isTimedOut(long ttlMillis) {
		long elapsed = System.nanoTime();
		long ttlNanos = ttlMillis * 1000 * 1000;
		if (elapsed - creationTime > ttlNanos) {
			timedout = true;
		} else {
			if (timedout) {
				throw new IllegalArgumentException("Operation has already timed out; ttl " +
								   "specified would allow it to be valid.");
			}
		}
		return timedout;
        }
