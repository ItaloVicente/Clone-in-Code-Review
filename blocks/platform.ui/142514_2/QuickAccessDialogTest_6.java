		assertTrue("Show all should be turned off when the shell is closed and reopened",
				newTable.getItemCount() < newCount);
	}

	public void testPreviousChoicesAvailable() {
		final IWorkbenchWindow firstWorkbenchWindow = openTestWindow();
		final QuickAccessDialog firstDialog = new QuickAccessDialog(firstWorkbenchWindow, null);
		firstDialog.open();
		Text text = firstDialog.getQuickAccessContents().getFilterText();
		String quickAccessElementText = "Project Explorer";
		text.setText(quickAccessElementText);
		Assert.assertTrue(processEventsUntil(
				() -> ContentMatchesTest.getAllEntries(firstDialog.getQuickAccessContents().getTable()).get(0)
				.toLowerCase()
						.contains(quickAccessElementText.toLowerCase()),
				600));
		firstDialog.getQuickAccessContents().getTable().select(0);
		Table table = firstDialog.getQuickAccessContents().getTable();
		table.notifyListeners(SWT.DefaultSelection, new Event());
		firstWorkbenchWindow.close();
		IWorkbenchWindow secondWorkbenchWindow = openTestWindow();
		final QuickAccessDialog secondDialog = new QuickAccessDialog(secondWorkbenchWindow, null);
		secondDialog.open();
		processEventsUntil(() -> secondDialog.getQuickAccessContents().getTable().getItemCount() > 1, 500);
		assertTrue(ContentMatchesTest.getAllEntries(secondDialog.getQuickAccessContents().getTable()).get(0)
				.contains(quickAccessElementText));
