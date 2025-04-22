	public void testCommandEnableContext() throws Exception {
		ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = commandService.getCommand("org.eclipse.ui.window.splitEditor");
		assertTrue(command.isDefined());

		File tmpFile = File.createTempFile("blah", ".txt");
		tmpFile.deleteOnExit();
		IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), tmpFile.toURI(),
				"org.eclipse.ui.DefaultTextEditor", true);

		QuickAccessDialog dialog = new QuickAccessDialog(activeWorkbenchWindow, null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		Table table = dialog.getQuickAccessContents().getTable();
		text.setText("Toggle Split");
		assertTrue("Not enough quick access items for simple filter",
				DisplayHelper.waitForCondition(table.getDisplay(), TIMEOUT, () -> table.getItemCount() > 1));
		assertTrue("Non-prefix match first", table.getItem(0).getText(1).toLowerCase().startsWith("toggle split"));
	}

