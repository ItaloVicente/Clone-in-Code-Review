
		if (status.getException() instanceof CoreException) {
			IStatus coreStatus = ((CoreException) status.getException()).getStatus();
			if (coreStatus != null) {
				LogEntry childEntry = createLogEntry(coreStatus);
				entry.addChild(childEntry);
			}
		}
