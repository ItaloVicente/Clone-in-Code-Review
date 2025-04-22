		Job job = Job.create(IDEWorkbenchMessages.ShowInSystemExplorerHandler_jobTitle, monitor -> {
			String logMsgPrefix;
			try {
				logMsgPrefix = event.getCommand().getName() + ": "; //$NON-NLS-1$
			} catch (NotDefinedException e1) {
				logMsgPrefix = event.getCommand().getId() + ": "; //$NON-NLS-1$
			}

			try {
				File canonicalPath = getSystemExplorerPath(item);
				if (canonicalPath == null) {
					return statusReporter.newStatus(IStatus.ERROR, logMsgPrefix
							+ IDEWorkbenchMessages.ShowInSystemExplorerHandler_notDetermineLocation, null);
