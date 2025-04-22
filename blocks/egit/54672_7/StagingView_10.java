	private static boolean noConflicts(IndexDiffData indexDiff) {
		return indexDiff == null ? true : indexDiff.getConflicting().isEmpty();
	}

	private static boolean indexDiffAvailable(IndexDiffData indexDiff) {
		return indexDiff == null ? false : true;
	}

	private boolean hasErrorsOrWarnings() {
		boolean checkEnabled = getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING);
		return checkEnabled ? (getProblemsSeverity() >= IMarker.SEVERITY_WARNING
				&& !ignoreErrors.getSelection()) : false;
	}

