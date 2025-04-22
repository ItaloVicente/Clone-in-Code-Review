		longEventThreshold = createIntegerEditor(
				PreferenceConstants.LONG_EVENT_THRESHOLD_MILLIS,
				Messages.MonitoringPreferencePage_long_event_threshold, topGroup, 3, HOUR_IN_MS);
		createIntegerEditor(
				PreferenceConstants.MAX_STACK_SAMPLES,
				Messages.MonitoringPreferencePage_max_stack_samples_label, topGroup, 1, 100);
		sampleInterval = createIntegerEditor(
				PreferenceConstants.SAMPLE_INTERVAL_MILLIS,
				Messages.MonitoringPreferencePage_sample_interval_label, topGroup, 2, HOUR_IN_MS);
		initialSampleDelay = createIntegerEditor(
				PreferenceConstants.INITIAL_SAMPLE_DELAY_MILLIS,
				Messages.MonitoringPreferencePage_initial_sample_delay_label, topGroup,
				2, HOUR_IN_MS);
		deadlockThreshold = createIntegerEditor(
				PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
				Messages.MonitoringPreferencePage_deadlock_threshold_label, topGroup,
				1000, 24 * HOUR_IN_MS);

		createBooleanEditor(PreferenceConstants.DUMP_ALL_THREADS,
