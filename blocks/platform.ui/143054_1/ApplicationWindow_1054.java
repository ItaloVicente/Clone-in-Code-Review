			final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		try {
			operationInProgress = true;
			final StatusLineManager mgr = getStatusLineManager();
			if (mgr == null) {
				runnable.run(new NullProgressMonitor());
				return;
			}
			boolean cancelWasEnabled = mgr.isCancelEnabled();

			final Control contents = getContents();
			final Display display = contents.getDisplay();
			Shell shell = getShell();
			boolean contentsWasEnabled = contents.getEnabled();
			MenuManager manager = getMenuBarManager();
			Menu menuBar = null;
			if (manager != null) {
				menuBar = manager.getMenu();
				manager = null;
			}
			boolean menuBarWasEnabled = false;
			if (menuBar != null) {
