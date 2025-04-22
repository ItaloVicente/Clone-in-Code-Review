
		final SelectionChangeListener listener = new SelectionChangeListener() {
			public void selectionChanged() {
				checkPreviousPagesSelections();
			}
		};
		repoPage.addSelectionListener(listener);
		refSpecPage.addSelectionListener(listener);
