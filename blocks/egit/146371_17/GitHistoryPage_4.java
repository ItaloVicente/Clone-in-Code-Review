	private static String getRepoSpecificKey(String repositoryId,
			String key) {
		return key + "_" + repositoryId; //$NON-NLS-1$
	}

	private static String getRepoSpecificKey(@NonNull Repository repo,
			String key) {
		String pathString = Activator.getDefault().getRepositoryUtil()
				.getRelativizedRepositoryPath(repo);

		if (pathString == null) {
			return getRepoSpecificKey(repo.toString(), key);
		}

		return getRepoSpecificKey(pathString, key);
	}

	private void unsetRepoSpecificPreference(String repositoryPath,
			String key) {
		String prefString = getRepoSpecificKey(repositoryPath, key);
		store.setToDefault(prefString);
	}

	private void saveStoreIfNeeded() {
		if (store.needsSaving()) {
			try {
				store.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
		}
	}

	private boolean isShowFirstParentOnly() {
		final String prefKey = UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT;
		boolean firstParent = store.getBoolean(prefKey);
		Repository repo = getCurrentRepo();

		if (repo != null) {
			String repoSpecificKey = getRepoSpecificKey(repo, prefKey);
			if (store.contains(repoSpecificKey)) {
				firstParent = store.getBoolean(repoSpecificKey);
			}
		}
		return firstParent;
	}

