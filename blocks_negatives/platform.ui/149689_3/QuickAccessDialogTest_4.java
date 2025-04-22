		text.notifyListeners(SWT.KeyDown, enterPressed);
		processEventsUntil(() -> text.isDisposed(), 500);
		dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		final Table secondTable = dialog.getQuickAccessContents().getTable();
		processEventsUntil(() -> secondTable.getItemCount() > 1, 500);
		assertTrue(getAllEntries(secondTable).get(0).contains(quickAccessElementText));
