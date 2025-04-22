	private static String getRepositorySpecificFirstParentPreferenceString(
			@NonNull Repository repo) {
		return UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY + "_" //$NON-NLS-1$
				+ repo.toString();
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

