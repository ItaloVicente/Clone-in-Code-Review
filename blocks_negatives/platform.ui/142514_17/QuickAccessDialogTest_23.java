		IHandlerService handlerService = getWorkbench().getActiveWorkbenchWindow().getService(IHandlerService.class);
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		handlerService.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
		final Table table = searchField.getQuickAccessTable();
		Text text = searchField.getQuickAccessSearchText();
