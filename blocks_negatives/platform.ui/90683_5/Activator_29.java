		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				ServiceTracker frameworkLogTracker = _frameworkLogTracker;
				FrameworkLog log = frameworkLogTracker == null ? null : (FrameworkLog) frameworkLogTracker.getService();
				if (log != null) {
					log.log(createLogEntry(status));
				} else {
					if( status.getException() != null ) {
						status.getException().printStackTrace(System.err);
					}
