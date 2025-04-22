		Policy.setLog(status -> {
			ServiceTracker frameworkLogTracker = _frameworkLogTracker;
			FrameworkLog log = frameworkLogTracker == null ? null : (FrameworkLog) frameworkLogTracker.getService();
			if (log != null) {
				log.log(createLogEntry(status));
			} else {
				System.err.println(status.getPlugin() + " - " + status.getCode() + " - " + status.getMessage()); //$NON-NLS-1$//$NON-NLS-2$
				if (status.getException() != null) {
					status.getException().printStackTrace(System.err);
