	private static boolean noConflicts(IndexDiffData indexDiff) {
		return indexDiff == null ? true : indexDiff.getConflicting().isEmpty();
	}

	private static boolean indexDiffAvailable(IndexDiffData indexDiff) {
		return indexDiff == null ? false : true;
	}

	private boolean hasErrorsOrWarnings() {
		return getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				? (getProblemsSeverity() >= Integer.valueOf(getPreferenceStore()
						.getString(UIPreferences.WARN_BEFORE_COMMITTING_LEVEL))
				&& !ignoreErrors.getSelection()) : false;
	}

	@SuppressWarnings("boxing")
	private boolean isCommitBlocked() {
		return getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT)
						? (getProblemsSeverity() >= Integer
								.valueOf(getPreferenceStore().getString(
										UIPreferences.BLOCK_COMMIT_LEVEL))
								&& !ignoreErrors.getSelection())
						: false;
	}

