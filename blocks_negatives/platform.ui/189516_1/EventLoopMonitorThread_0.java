		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			throw new IllegalStateException(Messages.EventLoopMonitorThread_workbench_was_null);
		}

		Display display = workbench.getDisplay();
