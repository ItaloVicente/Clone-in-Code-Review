	private final Listener closeListener = event -> {
		boolean doExit = IDEWorkbenchWindowAdvisor.promptOnExit(null);
		event.doit = doExit;
		if (!doExit)
			event.type = SWT.None;
	};

