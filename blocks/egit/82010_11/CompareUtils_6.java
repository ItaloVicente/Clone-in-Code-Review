		getSynchronizer().compare(file, repository, leftPath,
				rightPath,
				leftRev, rightRev, includeLocal, page);
	}

	private static GitSynchronizer getSynchronizer() {
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {
			return new ModelAwareGitSynchronizer();
