		String newRefFilters = ""; //$NON-NLS-1$
		Repository repo = getCurrentRepo();
		if (repo != null) {
			newRefFilters = store.getString(Activator.getDefault()
					.getRepositoryUtil()
					.getRepositorySpecificPreferenceKey(repo,
							UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS));
		}
		boolean refFiltersChanged = currentRefFilters != newRefFilters;
		currentRefFilters = newRefFilters;
