		final LogThresholdFieldEditor maxEventLogTime = new LogThresholdFieldEditor(
				PreferenceConstants.MAX_EVENT_LOG_TIME_MILLIS,
				Messages.MonitoringPreferencePage_event_log_label, topGroup);
		createIntegerFieldEditor(PreferenceConstants.MAX_LOG_TRACE_COUNT,
				Messages.MonitoringPreferencePage_stack_sample_label, topGroup);
		createIntegerFieldEditor(PreferenceConstants.SAMPLE_INTERVAL_TIME_MILLIS,
				Messages.MonitoringPreferencePage_sample_interval_label, topGroup);
		topGroup.setLayout(innerGroupLayout);

		IntegerFieldEditor maxEventSampleTime = new IntegerFieldEditor(
				PreferenceConstants.MAX_EVENT_SAMPLE_TIME_MILLIS,
				Messages.MonitoringPreferencePage_first_stack_label, topGroup) {
			@Override
			protected boolean checkState() {
				try {
					if (maxEventLogTime.getIntValue() < this.getIntValue()) {
						showErrorMessage();
						return false;
					}
				} catch (NumberFormatException e) {
				}
				return super.checkState();
			}
		};

		maxEventSampleTime.setValidRange(1, Integer.MAX_VALUE);
		maxEventSampleTime.setErrorMessage(Messages.MonitoringPreferencePage_capture_threshold_error);
		maxEventLogTime.setSampleFieldEditor(maxEventSampleTime);
		maxEventSampleTime.fillIntoGrid(topGroup, 2);
		addField(maxEventSampleTime, topGroup);
		maxEventSampleTime.setEnabled(pluginEnabled, topGroup);

		createIntegerFieldEditor(PreferenceConstants.FORCE_DEADLOCK_LOG_TIME_MILLIS,
				Messages.MonitoringPreferencePage_deadlock_label, topGroup);

		createBooleanFieldEditor(PreferenceConstants.DUMP_ALL_THREADS,
