	private ThreadLocal<Boolean> isUpdateJobRunning = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

