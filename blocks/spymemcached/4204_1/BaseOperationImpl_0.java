        @Override
        public void timedOut() {
            timedout = true;
        }

        @Override
        public boolean isTimedOut() {
            return timedout;
        }

	@Override
	public boolean isTimedOut(long ttl) {
		long elapsed = System.nanoTime();
		long ttlNanos = ttl * 1000 * 1000; /* ttl supplied is millis */
		if (elapsed - creationTime > ttlNanos)
			timedout = true;
		return timedout;
        }
