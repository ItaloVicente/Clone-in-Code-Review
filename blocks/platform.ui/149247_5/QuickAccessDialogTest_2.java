	public void testPreviousChoicesAvailableForIncrementalExtension() {
		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		text.setText(TestIncrementalQuickAccessComputer.ENABLEMENT_QUERY);
		final Table firstTable = dialog.getQuickAccessContents().getTable();
		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return TestIncrementalQuickAccessComputer.isContributedItem(getAllEntries(firstTable).get(0));
			}
		}.waitForCondition(text.getDisplay(), 200));
		firstTable.select(0);
		Event enterPressed = new Event();
		enterPressed.widget = text;
		enterPressed.keyCode = SWT.CR;
		text.notifyListeners(SWT.KeyDown, enterPressed);
		processEventsUntil(() -> text.isDisposed(), 500);
		dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		final Table secondTable = dialog.getQuickAccessContents().getTable();
		assertTrue("Contributed item not found in previous choices", new DisplayHelper() { //$NON-NLS-1$
			@Override
			protected boolean condition() {
				return getAllEntries(secondTable).stream()
						.anyMatch(TestIncrementalQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(secondTable.getDisplay(), 500));
	}

