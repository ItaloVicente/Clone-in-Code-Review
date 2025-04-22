	private static String getRepositorySpecificFirstParentPreferenceKey(String repositoryPath) {
		return UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT + "_" //$NON-NLS-1$
				+ repositoryPath;
	}

	private static String getRepositorySpecificFirstParentPreferenceKey(
			@NonNull Repository repo) {
		java.nio.file.Path workspacePath = ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().toPath();

		File dir = repo.getDirectory();

		if (dir == null) {
			return repo.toString();
		}

		String pathString = dir.getAbsolutePath();

		java.nio.file.Path path = java.nio.file.Paths
				.get(pathString);

		if (path.startsWith(workspacePath)) {
			pathString = workspacePath.relativize(path).toString();
		}

		return getRepositorySpecificFirstParentPreferenceKey(pathString);
	}

	private void unsetProjectSpecificFirstParentPreference(
			String repositoryPath) {
		String prefString = getRepositorySpecificFirstParentPreferenceKey(
				repositoryPath);
		store.setToDefault(prefString);
		if (store.needsSaving())
			try {
				store.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
	}

	private boolean isShowFirstParentOnly() {
		boolean firstParent = store.getBoolean(
				UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT);
		Repository repo = getCurrentRepo();

		if (repo != null) {
			String repoSepcificKey = getRepositorySpecificFirstParentPreferenceKey(
					repo);
			if (store.contains(repoSepcificKey)) {
				firstParent = store.getBoolean(repoSepcificKey);
			}
		}
		return firstParent;
	}

