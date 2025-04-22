	private IBaseLabelProvider createLabelProvider() {
		StagingViewLabelProvider baseProvider = new StagingViewLabelProvider();
		baseProvider.setFileNameMode(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));
		return new DelegatingStyledCellLabelProvider(baseProvider);
	}

	private IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}
