
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		final Runnable disconnectFromWorkspace = new Runnable() {

			int attempts;

			@Override
			public void run() {
				if (isWorkspaceLocked(workspace)) {
					if (attempts < 3) {
						attempts++;
						IDEWorkbenchPlugin.log(null, createErrorStatus("Workspace is locked, waiting...")); //$NON-NLS-1$
						Display.getCurrent().timerExec(1000 * attempts, this);
					} else {
						IDEWorkbenchPlugin.log(null, createErrorStatus("Workspace is locked and can't be saved.")); //$NON-NLS-1$
					}
					return;
				}
				disconnectFromWorkspace();
			}

			IStatus createErrorStatus(String exceptionMessage) {
				return new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						IDEWorkbenchMessages.ProblemsSavingWorkspace, new IllegalStateException(exceptionMessage));
			}
		};

		if (workspace != null) {
			if (isWorkspaceLocked(workspace)) {
				Display.getCurrent().asyncExec(disconnectFromWorkspace);
			} else {
				disconnectFromWorkspace.run();
			}
