
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

		if (!display.isDisposed() && workspace != null && isWorkspaceLocked(workspace)) {
			display.asyncExec(disconnectFromWorkspace);
