	private boolean canCommitWithCurrentErrorsAndWarnings(
			Collection<String> selectedFiles) {
		IPreferenceStore preferences = Activator.getDefault()
				.getPreferenceStore();
		return (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_SCOPE) != 0 || CommitErrorWarningsUtil
				.canCommitWithCurrentErrors(shell, repo, selectedFiles))
				&& (preferences
						.getInt(UIPreferences.COMMIT_WITH_WARNINGS_SCOPE) != 0 || CommitErrorWarningsUtil
						.canCommitWithCurrentWarnings(shell, repo,
								selectedFiles));
	}

	private boolean canCommitWithCurrentErrorsAndWarnings() {
		IPreferenceStore preferences = Activator.getDefault()
				.getPreferenceStore();
		return (preferences.getInt(UIPreferences.COMMIT_WITH_ERRORS_SCOPE) == 0 || CommitErrorWarningsUtil
				.canCommitWithCurrentErrors(shell, repo, new HashSet<String>()))
				&& (preferences
						.getInt(UIPreferences.COMMIT_WITH_WARNINGS_SCOPE) == 0 || CommitErrorWarningsUtil
						.canCommitWithCurrentWarnings(shell, repo,
								new HashSet<String>()));
