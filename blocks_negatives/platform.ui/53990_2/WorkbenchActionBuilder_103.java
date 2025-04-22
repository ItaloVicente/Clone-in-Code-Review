		window.getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (window.getShell() != null && !window.getShell().isDisposed()) {
					ICommandService commandService = window.getService(ICommandService.class);
					commandService.refreshElements(IWorkbenchCommandConstants.WINDOW_PIN_EDITOR, null);
				}
