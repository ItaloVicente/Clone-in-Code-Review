	public void refresh(String filter) {
		if (monitor != null) {
			monitor.setCanceled(true);
			monitor = null;
		}
		if (table != null) {
			boolean filterTextEmpty = filter.length() == 0;

			List<QuickAccessEntry> extraEntries = new ArrayList<>();
			if (filter.length() > MIN_SEARCH_LENGTH) {
				extraEntries.add(makeHelpSearchEntry(filter));
			}

			QuickAccessElement perfectMatch = getPerfectMatch(filter);

			monitor = new NullProgressMonitor();
			List<QuickAccessEntry>[] entries = computeMatchingEntries(filter, perfectMatch, extraEntries, monitor);
			int selectionIndex = refreshTable(perfectMatch, entries, extraEntries);

			if (table.getItemCount() > 0) {
				table.setSelection(selectionIndex);
				hideHintText();
			} else if (filterTextEmpty) {
				showHintText(QuickAccessMessages.QuickAccess_StartTypingToFindMatches, grayColor);
			} else {
				showHintText(QuickAccessMessages.QuickAccessContents_NoMatchingResults, grayColor);
			}

			updateInfoLabel();

			updateFeedback(filterTextEmpty, showAllMatches);
