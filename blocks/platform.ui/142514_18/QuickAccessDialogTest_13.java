		assertTrue("Show all should be turned off when the shell is closed and reopened",
				newTable.getItemCount() < newCount);
	}

	public void testPreviousChoicesAvailable() {
		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		Table firstTable = dialog.getQuickAccessContents().getTable();
		String quickAccessElementText = "Project Explorer";
		text.setText(quickAccessElementText);
		processEventsUntil(() -> getAllEntries(firstTable).get(0).toLowerCase()
				.contains(quickAccessElementText.toLowerCase()), 200);
		firstTable.select(0);
		Event enterPressed = new Event();
		enterPressed.widget = text;
		enterPressed.keyCode = SWT.CR;
		text.notifyListeners(SWT.KeyDown, enterPressed);
		processEventsUntil(() -> text.isDisposed(), 500);
		dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		final Table secondTable = dialog.getQuickAccessContents().getTable();
		processEventsUntil(() -> secondTable.getItemCount() > 1, 500);
		assertTrue(getAllEntries(secondTable).get(0).contains(quickAccessElementText));
	}

	public void testPreviousChoicesAvailableForExtension() {
		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		text.setText(TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL);
		final Table firstTable = dialog.getQuickAccessContents().getTable();
		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return TestQuickAccessComputer.isContributedItem(getAllEntries(firstTable).get(0));
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
				return getAllEntries(secondTable).stream().anyMatch(TestQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(secondTable.getDisplay(), 500));
	}

	private List<String> getAllEntries(Table table) {
		final int nbColumns = table.getColumnCount();
		return Arrays.stream(table.getItems()).map(item -> {
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < nbColumns; i++) {
				res.append(item.getText(i));
				res.append(" | ");
			}
			return res.toString();
		}).collect(Collectors.toList());
