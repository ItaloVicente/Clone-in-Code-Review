	public final void testSelectAllHandlerSendsSelectionEvent()
			throws ExecutionException, NotHandledException {
		final Shell dialog = new Shell(fWorkbench.getActiveWorkbenchWindow()
				.getShell());
		dialog.setLayout(new GridLayout());
		final Text text = new Text(dialog, SWT.SINGLE);
		text.setText("Mooooooooooooooooooooooooooooo");
		text.setLayoutData(new GridData());
		text.addSelectionListener(new SelectionAdapter() {
			@Override
