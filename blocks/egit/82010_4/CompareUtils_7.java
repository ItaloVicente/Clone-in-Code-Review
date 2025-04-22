		getSynchronizer().compare(file, repository, leftPath,
				rightPath,
				leftRev, rightRev, includeLocal, page);
	}

	private static GitSynchronizer getSynchronizer() {
		Display display = Display.getCurrent();
		Assert.isNotNull(display);
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {
			return new ModelAwareGitSynchronizer(display);
