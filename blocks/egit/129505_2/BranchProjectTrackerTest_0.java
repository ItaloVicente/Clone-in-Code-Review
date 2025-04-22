				.setValue(getPreferenceKey(BRANCH), "");
	}

	private String getPreferenceKey(String branch) {
		return ProjectTrackerPreferenceHelper.getPreferenceKey(repository,
				branch);
