	private void createFetchConfigurationGroup(Composite panel) {
		fetchConfigurationGroup = SWTUtils.createHFillGroup(panel,
				UIText.GerritConfigurationPage_groupFetch,
				SWTUtils.MARGINS_DEFAULT, 2);
		new Label(fetchConfigurationGroup, SWT.NULL)
				.setText(UIText.GerritConfigurationPage_ConfigureFetchReviewNotes);
	}


