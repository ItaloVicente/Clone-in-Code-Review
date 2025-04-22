	private void triggerIgnoreErrorsButtonVisibility() {
		if (getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			ignoreErrors.setVisible(true);
		} else {
			ignoreErrors.setVisible(false);
		}
	}

	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				stagedViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		for (StagingEntry entry : entries) {
			if (entry.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result < entry.getProblemSeverity()) {
					result = entry.getProblemSeverity();
				}
			}
		}
		return result;
	}

