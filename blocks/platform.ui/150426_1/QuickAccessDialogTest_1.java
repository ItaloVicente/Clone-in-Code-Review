	public void testPrefixMatchHavePriority() {
		QuickAccessDialog dialog = new QuickAccessDialog(activeWorkbenchWindow, null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		Table table = dialog.getQuickAccessContents().getTable();
		text.setText("P");
		assertTrue("Not enough quick access items for simple filter",
				DisplayHelper.waitForCondition(table.getDisplay(), TIMEOUT, () -> table.getItemCount() > 3));
		assertTrue("Non-prefix match first", table.getItem(0).getText(1).toLowerCase().startsWith("p"));
	}

