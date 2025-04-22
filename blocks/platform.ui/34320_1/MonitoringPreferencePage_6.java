				Messages.MonitoringPreferencePage_enable_monitoring_label, topGroup);

		longEventWarningThreshold = createIntegerEditor(
				PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS,
				Messages.MonitoringPreferencePage_long_event_warning_threshold_label, topGroup,
				3, HOUR_IN_MS);
		longEventErrorThreshold = createIntegerEditor(
				PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS,
				Messages.MonitoringPreferencePage_long_event_error_threshold_label, topGroup,
				3, HOUR_IN_MS);
