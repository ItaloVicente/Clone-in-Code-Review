
		if (!RebaseInteractivePreferences.isOrderReversed()) {
			itemMoveUp.setEnabled(stepList.indexOf(firstSelectedEntry) > 0);
			itemMoveDown
					.setEnabled(stepList.indexOf(lastSelectedEntry) < stepList
							.size() - 1);
		} else {
			itemMoveUp
					.setEnabled(stepList.indexOf(firstSelectedEntry) < stepList
							.size() - 1);
			itemMoveDown
					.setEnabled(stepList.indexOf(lastSelectedEntry) > 0);
		}
