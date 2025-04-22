	/**
	 * Compute a delay in a {@code min..max} interval with random jitter.
	 *
	 * @param last
	 *            amount of delay waited before the last attempt. This is used
	 *            to seed the next delay interval. Should be 0 if there was no
	 *            prior delay.
	 * @param min
	 *            shortest amount of allowable delay between attempts.
	 * @param max
	 *            longest amount of allowable delay between attempts.
	 * @return new amount of delay to wait before the next attempt.
	 */
	static long delay(long last, long min, long max) {
		long r = Math.max(0, last * 3 - min);
		if (r > 0) {
			int c = (int) Math.min(r + 1, Integer.MAX_VALUE);
			r = RNG.nextInt(c);
		}
		return Math.max(Math.min(min + r, max), min);
	}
