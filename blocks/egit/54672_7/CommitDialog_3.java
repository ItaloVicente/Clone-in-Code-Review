		boolean hasErrorsOrWarnings = getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)
						? (getProblemsSeverity() >= IMarker.SEVERITY_WARNING
								&& !ignoreErrors.getSelection())
						: false;
		if (hasErrorsOrWarnings) {
			message = UIText.CommitDialog_MessageErrors;
			type = IMessageProvider.WARNING;
