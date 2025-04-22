
		final SelectionChangeListener listener = new SelectionChangeListener() {
			public void selectionChanged() {
				checkPreviousPagesSelections();
			}
		};
		sourcePage.addSelectionListener(listener);
		branchPage.addSelectionListener(listener);
