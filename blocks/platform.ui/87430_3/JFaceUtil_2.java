		Policy.setLog(status -> {
			if (status.getSeverity() == IStatus.WARNING
					|| status.getSeverity() == IStatus.ERROR) {
				StatusManager.getManager().handle(status);
			} else {
				WorkbenchPlugin.log(status);
