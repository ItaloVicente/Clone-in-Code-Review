	/**
	 * Tracks and reports potential deadlocks.
	 */
	private class DeadlockTracker {
		private boolean haveAlreadyLoggedPossibleDeadlock;

		private long lastActive;

		/**
		 * Logs a possible deadlock to the remote log. {@code lastActive} is zero if the interval is
		 * for a sleep, in which case we don't log a deadlock.
		 *
		 * @param currTime the current time
		 * @param stackSamples stack trace samples for the currently stalled event
		 * @param numSamples the number of valid stack trace samples in the stackSamples array
		 */
		public void logPossibleDeadlock(long currTime, StackSample[] stackSamples, int numSamples) {
			long totalDuration = currTime - lastActive;

			if (!haveAlreadyLoggedPossibleDeadlock && lastActive > 0 &&
					totalDuration > deadlockThreshold &&
					uiThreadFilter.shouldLogEvent(stackSamples, numSamples, uiThreadId)) {
				stackSamples = Arrays.copyOf(stackSamples, numSamples);
				logEvent(new UiFreezeEvent(lastActive, totalDuration,
						Arrays.copyOf(stackSamples, numSamples), true));
				haveAlreadyLoggedPossibleDeadlock = true;
				Arrays.fill(stackSamples, null);
			}
		}

		/**
		 * Resets the deadlock tracker's state.
		 */
		public void reset(long lastActive) {
			this.lastActive = lastActive;
			haveAlreadyLoggedPossibleDeadlock = false;
		}
	}

