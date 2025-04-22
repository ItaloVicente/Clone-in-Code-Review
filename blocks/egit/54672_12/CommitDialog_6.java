		boolean ignoreErrorsValue = ignoreErrors == null ? true
				: !ignoreErrors.getSelection();
		@SuppressWarnings("boxing")
		boolean hasErrorsOrWarnings = getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
						? (getProblemsSeverity() >= Integer
								.valueOf(getPreferenceStore().getString(
										UIPreferences.WARN_BEFORE_COMMITTING_LEVEL))
								&& ignoreErrorsValue)
						: false;
		if (hasErrorsOrWarnings) {
			message = UIText.CommitDialog_MessageErrors;
			type = IMessageProvider.WARNING;
