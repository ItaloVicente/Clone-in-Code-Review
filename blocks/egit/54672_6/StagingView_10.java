	private boolean hasErrorsOrWarnings() {
		boolean checkEnabled = getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING);
		return checkEnabled ? (getProblemsSeverity() >= IMarker.SEVERITY_WARNING
				&& !ignoreErrors.getSelection()) : false;
	}

