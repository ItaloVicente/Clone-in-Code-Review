		fileNameModeAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));

		IMenuManager dropdownMenu = getViewSite().getActionBars()
				.getMenuManager();
		dropdownMenu.add(openNewCommitsAction);
		dropdownMenu.add(fileNameModeAction);
	}

	private IBaseLabelProvider createLabelProvider() {
		StagingViewLabelProvider baseProvider = new StagingViewLabelProvider();
		baseProvider.setFileNameMode(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));
		return new DelegatingStyledCellLabelProvider(baseProvider);
	}

	private IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	private StagingViewLabelProvider getLabelProvider(ContentViewer viewer) {
		IBaseLabelProvider base = viewer.getLabelProvider();
		IStyledLabelProvider styled = ((DelegatingStyledCellLabelProvider) base)
				.getStyledStringProvider();
		return (StagingViewLabelProvider) styled;
