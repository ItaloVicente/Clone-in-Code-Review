	/**
	 * Get the name of the filters preference for the receiver,
	 *
	 * @return String
	 */
	private String getLegacyFiltersPreferenceName() {
		if (viewId != null && viewId.equals(IPageLayout.ID_BOOKMARKS)) {
			return IDEInternalPreferences.BOOKMARKS_FILTERS;
		}
		if (viewId != null && viewId.equals(IPageLayout.ID_TASK_LIST)) {
			return IDEInternalPreferences.TASKS_FILTERS;
		}
		return IDEInternalPreferences.PROBLEMS_FILTERS;

	}
