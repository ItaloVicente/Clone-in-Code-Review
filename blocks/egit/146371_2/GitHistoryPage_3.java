	private static String getRepositorySpecificFirstParentPreferenceString(
			Repository repo) {
		return UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY + "_" //$NON-NLS-1$
				+ repo.toString();
	}

	private boolean isFirstParent() {
		boolean firstParent;
		String repoSepcificString = getRepositorySpecificFirstParentPreferenceString(
				getCurrentRepo());
		if (store.contains(repoSepcificString)) {
			firstParent = store.getBoolean(repoSepcificString);
		} else {
			firstParent = store.getBoolean(
					UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY);
		}
		return firstParent;
	}

