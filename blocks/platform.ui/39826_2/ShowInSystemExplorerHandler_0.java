		Job job = new Job(IDEWorkbenchMessages.ShowInSystemExplorerHandler_jobTitle) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				String logMsgPrefix;
				try {
					logMsgPrefix = event.getCommand().getName() + ": "; //$NON-NLS-1$
				} catch (NotDefinedException e) {
					logMsgPrefix = event.getCommand().getId() + ": "; //$NON-NLS-1$
				}

				try {
					File canonicalPath = getSystemExplorerPath(item);
					if (canonicalPath == null) {
						return new Status(
