		Listener closeListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean doExit = IDEWorkbenchWindowAdvisor.promptOnExit(null);
				event.doit = doExit;
				if (!doExit)
					event.type = SWT.None;
			}
		};
