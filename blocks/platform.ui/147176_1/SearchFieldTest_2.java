	public void testPreviousChoicesAvailableForExtension() {
		createSearchField();
		Text text = searchField.getQuickAccessSearchText();
		text.setText(TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL);
		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return TestQuickAccessComputer
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
				return getAllEntries(searchField.getQuickAccessTable()).stream().anyMatch(TestQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(searchField.getQuickAccessTable().getDisplay(), 500));
	}

