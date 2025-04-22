		editorInfo = null;
	}

	boolean mergeInto(NavigationHistoryEntry currentEntry) {
		if (editorInfo.editorInput != null && editorInfo.editorInput.equals(currentEntry.editorInfo.editorInput)) {
			if (location != null) {
				if (currentEntry.location == null) {
					currentEntry.location = location;
					location = null;
					return true;
				}
