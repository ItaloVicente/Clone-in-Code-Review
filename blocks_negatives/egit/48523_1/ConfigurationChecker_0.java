	private static void checkGitPrefix() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean hidden = !store
				.getBoolean(UIPreferences.SHOW_GIT_PREFIX_WARNING);
		if (!hidden && FS.DETECTED.gitPrefix() == null)
			Activator.handleIssue(IStatus.WARNING,
					UIText.ConfigurationChecker_gitPrefixWarningMessage, null,
					false);
	}

