		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(
				mock);

		runOnThread(() -> {
			try {
				pm.start(1);
				fail("start did not fail on background thread");
			} catch (IllegalStateException notMainThread) {
			}
