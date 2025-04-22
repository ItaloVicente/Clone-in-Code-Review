		updateProjectsListAndPreventFocusLostHandling(path, false);
	}

	private void updateProjectsListAndPreventFocusLostHandling(final String path, boolean forceUpdate) {
		isUpdatingProjectsList = true;
		try {
			updateProjectsList(path, forceUpdate);
		} finally {
			isUpdatingProjectsList = false;
		}
