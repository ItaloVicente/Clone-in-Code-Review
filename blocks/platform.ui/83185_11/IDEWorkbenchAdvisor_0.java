
		Display display = getWorkbenchConfigurer().getWorkbench().getDisplay();
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		final Runnable disconnectFromWorkspace = new Runnable() {
			@Override
			public void run() {
				if (isWorkspaceLocked(workspace)) {
					display.asyncExec(this);
					return;
				}
				disconnectFromWorkspace();
			}
		};

		if (workspace != null) {
			if (!display.isDisposed() && isWorkspaceLocked(workspace)) {
				display.asyncExec(disconnectFromWorkspace);
			} else {
				disconnectFromWorkspace.run();
			}
