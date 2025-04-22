	private LogListener listener = entry -> {
		if (!this.logged) {
			this.logged = entry.getLogLevel() == LogLevel.ERROR;
		}
	};
	private boolean logged = false;
