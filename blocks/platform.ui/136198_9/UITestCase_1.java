		Display display = shell.getDisplay();
		if (display.getActiveShell() != shell) {
			forceActive(shell);
		}
		long endTime = System.currentTimeMillis() + 1000;
		while (display.getActiveShell() != shell && System.currentTimeMillis() < endTime) {
			processEvents();
		}
		Shell activeShell = display.getActiveShell();
		if (activeShell != shell) {
			String id = "org.eclipse.ui.tests.harness";
			Platform.getLog(Platform.getBundle(id)).log(new Status(IStatus.WARNING, id,
					"Failed to make shell active: " + shell + ", current active shell is: " + activeShell));
		}
