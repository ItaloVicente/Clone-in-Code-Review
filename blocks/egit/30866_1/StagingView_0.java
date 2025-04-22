	private StagingViewContentProvider createStagingContentProvider(
			boolean unstaged) {
		StagingViewContentProvider provider = new StagingViewContentProvider(
				this, unstaged);
		provider.setFileNameMode(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));
		return provider;
	}

