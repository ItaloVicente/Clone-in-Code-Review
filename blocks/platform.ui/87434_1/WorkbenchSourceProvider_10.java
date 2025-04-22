		final Map currentState = getCurrentState();
		final Shell newActiveShell = (Shell) currentState
				.get(ISources.ACTIVE_SHELL_NAME);
		final WorkbenchWindow newActiveWorkbenchWindow = (WorkbenchWindow) currentState
				.get(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
		final Shell newActiveWorkbenchWindowShell = (Shell) currentState
				.get(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME);
