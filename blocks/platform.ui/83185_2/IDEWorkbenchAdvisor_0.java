
		Display display = getWorkbenchConfigurer().getWorkbench().getDisplay();

		final Runnable disconnectFromWorkspace = new Runnable() {
			@Override
			public void run() {
				IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
				if (workspace != null) {
					ISchedulingRule currentRule = Job.getJobManager().currentRule();
					if (currentRule != null && currentRule.isConflicting(workspace.getRoot())) {
						display.asyncExec(this);
						return;
					}
					disconnectFromWorkspace();
				}
			}
		};

		display.asyncExec(disconnectFromWorkspace);
