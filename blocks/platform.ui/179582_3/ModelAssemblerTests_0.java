	private static final int COUNTDOWN_TIMEOUT = 10_000;
	private ArrayDeque<String> logMessages;
	private ModelAssemblerTestLogListener logListener;

	class ModelAssemblerTestLogListener implements LogListener {

		CountDownLatch countDownLatch;

		@Override
		public void logged(LogEntry entry) {
			logMessages.add(entry.getMessage());
			if (countDownLatch != null) {
				countDownLatch.countDown();
			}
		}

	}

