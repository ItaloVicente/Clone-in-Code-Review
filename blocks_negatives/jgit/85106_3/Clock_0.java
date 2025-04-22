
	/**
	 * Obtains a time with {@code propose()} and waits up to 5 seconds.
	 *
	 * @return milliseconds since the epoch.
	 * @throws IllegalStateException
	 *             if the thread is interrupted, or the default wait of 5
	 *             seconds is not long enough.
	 */
	public default long now() {
		try (ProposedTimestamp p = propose()) {
			p.blockUntil(5, TimeUnit.SECONDS);
			return p.getMillis();
		} catch (InterruptedException | TimeoutException e) {
			throw new IllegalStateException(JGitText.get().timeIsUncertain, e);
		}
	}
