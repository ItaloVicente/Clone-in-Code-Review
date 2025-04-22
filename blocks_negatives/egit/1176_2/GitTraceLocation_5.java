
	private static final class DebugTraceImpl implements DebugTrace {

		private ILog myLog;

		public void trace(String location, String message) {
			getLog().log(
					new Status(IStatus.INFO, Activator.getPluginId(), message));

		}

		public void trace(String location, String message, Throwable error) {

			getLog().log(
					new Status(IStatus.INFO, Activator.getPluginId(), message));
			if (error != null)
				getLog().log(
						new Status(IStatus.INFO, Activator.getPluginId(), error
								.getMessage()));

		}

		public void traceEntry(String location) {
		}

		public void traceEntry(String location, String message) {
		}

		private ILog getLog() {
			if (myLog == null) {
				myLog = Activator.getDefault().getLog();
			}
			return myLog;
		}

	}

