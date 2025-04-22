	private LogEntry createLogEntry(FrameworkLogEntry input) {
		IStatus status = new Status(input.getSeverity(), input.getEntry(), input.getMessage(), input.getThrowable());
		LogEntry logEntry = createLogEntry(status);
		FrameworkLogEntry[] children = input.getChildren();
		if (children != null) {
			for (FrameworkLogEntry child : children) {
				LogEntry entry = createLogEntry(child);
				logEntry.addChild(entry);
			}
		}
		return logEntry;
	}

