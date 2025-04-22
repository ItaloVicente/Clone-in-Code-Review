
		{
			Composite topGroup = new Composite(container, SWT.NONE);

			monitoringEnabled = createBooleanEditor(PreferenceConstants.MONITORING_ENABLED,
					Messages.MonitoringPreferencePage_enable_monitoring_label, topGroup);
			createBooleanEditor(PreferenceConstants.LOG_TO_ERROR_LOG,
					Messages.MonitoringPreferencePage_log_freeze_events_label, topGroup);

			longEventWarningThreshold = createIntegerEditor(
					PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS,
					Messages.MonitoringPreferencePage_warning_threshold_label, topGroup,
					3, HOUR_IN_MS);
			longEventErrorThreshold = createIntegerEditor(
					PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS,
					Messages.MonitoringPreferencePage_error_threshold_label, topGroup,
					3, HOUR_IN_MS);
			deadlockThreshold = createIntegerEditor(
					PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
					Messages.MonitoringPreferencePage_deadlock_threshold_label, topGroup,
					1000, 24 * HOUR_IN_MS);
			createIntegerEditor(
					PreferenceConstants.MAX_STACK_SAMPLES,
					Messages.MonitoringPreferencePage_max_stack_samples_label, topGroup, 0, 100);
			GridLayoutFactory.fillDefaults()
					.numColumns(2)
					.spacing(LayoutConstants.getSpacing())
					.applyTo(topGroup);
		}

		{
			final Composite bottomGroup = new Composite(container, SWT.NONE);

			createEmptySpace(bottomGroup, pixelConverter.convertVerticalDLUsToPixels(3), 2);
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
			GridLayoutFactory.fillDefaults()
					.numColumns(2)
					.spacing(LayoutConstants.getSpacing())
					.applyTo(bottomGroup);
		}

		GridLayoutFactory.fillDefaults()
				.numColumns(1)
				.spacing(LayoutConstants.getSpacing())
				.generateLayout(container);

		GridLayoutFactory.fillDefaults()
				.numColumns(1)
				.spacing(LayoutConstants.getSpacing())
				.generateLayout(parent);
