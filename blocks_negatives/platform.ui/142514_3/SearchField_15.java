		if (restoreDialogEntriesJob != null) {
			restoreDialogEntriesJob.cancel();
		}
		if (refreshQuickAccessContents != null) {
			refreshQuickAccessContents.cancel();
		}
		storeDialog();
		elementMap.clear();
		previousPicksList.clear();
		textMap.clear();
		partService = null;
