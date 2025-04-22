	ILogger logger = new ILogger() {
		@Override
		public void log(IStatus status) {
			if (!status.isOK()) {
				errorStatusses.add(status);
			}
