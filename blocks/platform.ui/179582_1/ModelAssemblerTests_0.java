	private ArrayDeque<String> logMessages = new ArrayDeque<>();
	private ModelAssemblerTestLogListener logListener = new ModelAssemblerTestLogListener();

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

