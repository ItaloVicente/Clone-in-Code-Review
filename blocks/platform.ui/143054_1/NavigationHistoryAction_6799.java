		final NavigationHistory history = (NavigationHistory) getWorkbenchWindow().getActivePage()
				.getNavigationHistory();
		NavigationHistoryEntry[] entries;
		if (forward) {
			entries = history.getForwardEntries();
		} else {
			entries = history.getBackwardEntries();
		}
		int entriesCount[] = new int[entries.length];
		for (int i = 0; i < entriesCount.length; i++) {
			entriesCount[i] = 1;
		}
		entries = collapseEntries(entries, entriesCount);
		for (int i = 0; i < entries.length; i++) {
			if (i > MAX_HISTORY_LENGTH) {
				break;
			}
			String text = entries[i].getHistoryText();
			if (text != null) {
				MenuItem item = new MenuItem(menu, SWT.NONE);
				item.setData(entries[i]);
				if (entriesCount[i] > 1) {
