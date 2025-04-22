	private LogEntry createLogEntry(org.osgi.service.log.LogEntry input) {
		LogLevel logLevel = input.getLogLevel();
		int severity;
		switch (logLevel) {
		case ERROR:
			severity = IStatus.ERROR;
			break;
		case WARN:
			severity = IStatus.WARNING;
			break;
		case INFO:
			severity = IStatus.INFO;
			break;
		case DEBUG:
		default:
			severity = IStatus.OK;
		}
		IStatus status = new Status(severity, input.getBundle().getSymbolicName(), input.getMessage(),
				input.getException());
		return createLogEntry(status);
	}

