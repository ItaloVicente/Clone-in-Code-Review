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
		dialogShell.pack();
		dialogShell.open();
		final Display display = dialogShell.getDisplay();
		while (display.readAndDispatch())
			;

		final Object dialogResult = new Object();
		final IHandler dialogHandler = new AbstractHandler() {

			public Object execute(Map parameterValuesByName)
					throws ExecutionException {
				return dialogResult;
			}
		};
		final HandlerSubmission dialogSubmission = new HandlerSubmission(null,
				dialogShell, null, commandId, dialogHandler, Priority.MEDIUM);
		commandSupport.addHandlerSubmission(dialogSubmission);

		final ICommand command = commandSupport.getCommandManager().getCommand(
				commandId);
		assertSame(
				"The active shell must be the dialog.  If you are activating other shells while this test is running, then this test will fail",
				dialogShell, display.getActiveShell());
		assertSame(
				"The active workbench window must be the window created in this test.  If you are activating other workbench windows, then this test will fail",
				windowShell, fWorkbench.getActiveWorkbenchWindow().getShell());
		final Object result = command.execute(Collections.EMPTY_MAP);
		assertSame(
				"The dialog handler was not chosen when both a window and dialog handler were defined.",
				dialogResult, result);

		commandSupport.removeHandlerSubmission(windowSubmission);
		commandSupport.removeHandlerSubmission(dialogSubmission);
		dialogShell.close();
		while (display.readAndDispatch())
			;
