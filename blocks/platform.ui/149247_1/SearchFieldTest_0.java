	public void testPreviousChoicesAvailableForIncrementalExtension() {
		createSearchField();
		Text text = searchField.getQuickAccessSearchText();
		text.setText(TestIncrementalQuickAccessComputer.ENABLEMENT_QUERY);
		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return TestIncrementalQuickAccessComputer
						.isContributedItem(getAllEntries(searchField.getQuickAccessTable()).get(0));
			}
		}.waitForCondition(text.getDisplay(), 200));
		searchField.getQuickAccessTable().select(0);
		Event enterPressed = new Event();
		enterPressed.widget = text;
		enterPressed.keyCode = SWT.CR;
		text.notifyListeners(SWT.KeyDown, enterPressed);
		workbenchWindow.close(true);
		processEventsUntil(() -> searchField.getQuickAccessSearchText().isDisposed(), 500);
		createSearchField();
		searchField.activate(searchField.getQuickAccessSearchText());
		assertTrue("Contributed item not found in previous choices", new DisplayHelper() { //$NON-NLS-1$
			@Override
			protected boolean condition() {
				return getAllEntries(searchField.getQuickAccessTable()).stream()
						.anyMatch(TestIncrementalQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(searchField.getQuickAccessTable().getDisplay(), 500));
	}

