
		int firstEntryIndex = stepList.indexOf(firstSelectedEntry);
		int lastEntryIndex = stepList.indexOf(lastSelectedEntry);
		if (!RebaseInteractivePreferences.isOrderReversed()) {
			itemMoveUp.setEnabled(firstEntryIndex > 0);
			itemMoveDown.setEnabled(lastEntryIndex < stepList.size() - 1);
		} else {
			itemMoveUp.setEnabled(firstEntryIndex < stepList.size() - 1);
			itemMoveDown.setEnabled(lastEntryIndex > 0);
		}
