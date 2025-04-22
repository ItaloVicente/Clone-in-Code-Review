		@SuppressWarnings("boxing")
		boolean commitBlocked = getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT)
						? (getProblemsSeverity() >= Integer
								.valueOf(getPreferenceStore().getString(
										UIPreferences.BLOCK_COMMIT_LEVEL))
								&& ignoreErrorsValue)
						: false;
		boolean commitEnabled = (type == IMessageProvider.WARNING
				|| type == IMessageProvider.NONE) && !commitBlocked;
		updateCommitButtons(commitEnabled);
