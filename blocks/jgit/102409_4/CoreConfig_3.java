	public static enum LogAllRefUpdates {
		FALSE(r -> false)

		TRUE(r -> r.startsWith(Constants.R_HEADS)
				|| r.startsWith(Constants.R_REMOTES)
				|| r.startsWith(Constants.R_NOTES)
				|| r.equals(Constants.HEAD))

		ALWAYS(r -> true);

		private final Predicate<String> shouldAutoCreateLog;

		private LogAllRefUpdates(Predicate<String> shouldAutoCreateLog) {
			this.shouldAutoCreateLog = shouldAutoCreateLog;
		}

		public boolean shouldAutoCreateLog(String refName) {
			return shouldAutoCreateLog.test(refName);
		}
	}

