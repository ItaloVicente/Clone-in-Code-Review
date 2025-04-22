	private void updateIgnoreErrorsButtonVisibility() {
		boolean visible = getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING);
		showControl(ignoreErrors, visible);
		ignoreErrors.getParent().layout(true);
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

