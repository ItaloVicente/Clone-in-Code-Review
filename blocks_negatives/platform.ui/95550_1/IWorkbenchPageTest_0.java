	ILogListener openAndHideListener = new ILogListener() {
		@Override
		public void logging(IStatus status, String plugin) {
			logStatus = status;
			logCount++;
		}
