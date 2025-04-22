		getSyncStrategy(resources, repository, leftRev, rightRev, includeLocal)
				.compare(leftPath, rightPath, page);
	}

	private static SyncStrategy getSyncStrategy(IResource[] resources,
			@NonNull Repository repository, String leftRev, String rightRev,
			boolean includeLocal) {
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {
			return new ModelAwareSyncStrategy(resources, repository, leftRev,
