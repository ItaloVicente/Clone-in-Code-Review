	private LogListener listener = new LogListener() {
		@Override
		public void logged(LogEntry entry) {
			if (!logged) {
				logged = entry.getLogLevel() == LogLevel.ERROR;
			}
