	private static String getRepositorySpecificFirstParentPreferenceString(String repositoryPath) {
		return UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY + "_" //$NON-NLS-1$
				+ repositoryPath;
	}

	private static String getRepositorySpecificFirstParentPreferenceString(
			@NonNull Repository repo) {
		java.nio.file.Path workspacePath = ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().toPath();

		File dir = repo.getDirectory();

		if (dir == null) {
			return repo.toString();
		}

		String pathString = dir.getAbsolutePath();

		java.nio.file.Path p = java.nio.file.Paths
				.get(repo.getDirectory().getAbsolutePath());

		if (p.startsWith(workspacePath)) {
			pathString = workspacePath.relativize(p).toString();
		}

		return getRepositorySpecificFirstParentPreferenceString(pathString);
	}

	private void unsetProjectSpecificFirstParentPreference(
			String repositoryPath) {
		String prefString = getRepositorySpecificFirstParentPreferenceString(
				repositoryPath);
		store.setToDefault(prefString);
		if (store.needsSaving())
			try {
				store.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
	}

	private boolean isFirstParent() {
		boolean firstParent = store.getBoolean(
				UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY);
		Repository repo = getCurrentRepo();

		if (repo != null) {
			String repoSepcificString = getRepositorySpecificFirstParentPreferenceString(
					repo);
			if (store.contains(repoSepcificString)) {
				firstParent = store.getBoolean(repoSepcificString);
			}
		}
		return firstParent;
	}

