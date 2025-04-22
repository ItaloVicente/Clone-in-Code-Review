
		if (table.getItemCount() > 0) {
			table.setSelection(selectionIndex);
			hideHintText();
		} else if (filter.isEmpty()) {
			showHintText(QuickAccessMessages.QuickAccess_StartTypingToFindMatches, grayColor);
		} else {
			showHintText(QuickAccessMessages.QuickAccessContents_NoMatchingResults, grayColor);
		}
		updateInfoLabel();
		updateFeedback(filter.isEmpty(), showAllMatches);
