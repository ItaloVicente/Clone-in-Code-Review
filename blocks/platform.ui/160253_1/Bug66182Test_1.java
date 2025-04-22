		final IWorkbenchWindow window = openTestWindow();

		final Object windowResult = new Object();
		final IHandler windowHandler = new AbstractHandler() {

			public Object execute(Map parameterValuesByName)
					throws ExecutionException {
				return windowResult;
			}
		};
		final IWorkbenchCommandSupport commandSupport = fWorkbench
				.getCommandSupport();
		final String commandId = "org.eclipse.ui.tests.Bug66182";
		final Shell windowShell = window.getShell();
		final HandlerSubmission windowSubmission = new HandlerSubmission(null,
				windowShell, null, commandId, windowHandler, Priority.MEDIUM);
		commandSupport.addHandlerSubmission(windowSubmission);

		final Shell dialogShell = new Shell(windowShell);
		final IWorkbenchContextSupport contextSupport = fWorkbench
				.getContextSupport();
		contextSupport.registerShell(dialogShell,
				IWorkbenchContextSupport.TYPE_WINDOW);
		dialogShell.pack();
		dialogShell.open();
		final Display display = dialogShell.getDisplay();
		while (display.readAndDispatch())
			;

		final ICommand command = commandSupport.getCommandManager().getCommand(
				commandId);
		assertSame(
				"The active shell must be the dialog.  If you are activating other shells while this test is running, then this test will fail",
				dialogShell, display.getActiveShell());
		assertSame(
				"The active workbench window must be the window created in this test.  If you are activating other workbench windows, then this test will fail",
				windowShell, fWorkbench.getActiveWorkbenchWindow().getShell());
		final Object result = command.execute(new HashMap());
		assertSame(
				"The window handler was not chosen when both a dialog was open with no handler, but the active workbench window did have a handler.",
				windowResult, result);

		commandSupport.removeHandlerSubmission(windowSubmission);
		contextSupport.unregisterShell(dialogShell);
		dialogShell.close();
		while (display.readAndDispatch())
			;
