		FilterListEditor uiThreadFilter = new FilterListEditor(PreferenceConstants.UI_THREAD_FILTER,
				Messages.MonitoringPreferencePage_ui_thread_filter_label,
				Messages.MonitoringPreferencePage_add_ui_thread_filter_button_label,
				Messages.MonitoringPreferencePage_remove_ui_thread_filter_button_label,
				Messages.FilterInputDialog_ui_thread_filter_message,
				bottomGroup);
		addField(uiThreadFilter, bottomGroup);

		createEmptySpace(bottomGroup, pixelConverter.convertVerticalDLUsToPixels(3), 2);
		FilterListEditor noninterestingThreadFilter = new FilterListEditor(
				PreferenceConstants.NONINTERESTING_THREAD_FILTER,
				Messages.MonitoringPreferencePage_noninteresting_thread_filter_label,
				Messages.MonitoringPreferencePage_add_noninteresting_thread_filter_button_label,
				Messages.MonitoringPreferencePage_remove_noninteresting_thread_filter_button_label,
				Messages.FilterInputDialog_noninteresting_thread_filter_message,
				bottomGroup);
		addField(noninterestingThreadFilter, bottomGroup);
	}

	private static Control createEmptySpace(Composite parent, int height, int span) {
		Label label= new Label(parent, SWT.LEFT);
		GridData gd= new GridData();
		gd.horizontalAlignment= GridData.BEGINNING;
		gd.grabExcessHorizontalSpace= false;
		gd.horizontalSpan= span;
		gd.horizontalIndent= 0;
		gd.widthHint= 0;
		gd.heightHint= height;
		label.setLayoutData(gd);
		return label;
