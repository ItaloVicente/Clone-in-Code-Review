	/**
	 * Test the cost of {@link SubMonitor#step(int)}
	 */
	public void testJobSubMonitorStep() throws Exception {
		openTestWindow();
		setRunInBackground(true);
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				SubMonitor subMonitor = SubMonitor.convert(monitor, ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					subMonitor.step(1);
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

