
		CancelabilityMonitor.BasicOptionsImpl jobCancelabilityMonitorOptions = null;
		if (preferences.getBoolean(PreferenceConstants.TASK_MONITORING_ENABLED)) {
			jobCancelabilityMonitorOptions = createCancelabilityMonitorService(preferences);
		}

		preferences.addPropertyChangeListener(new CancelabilityMonitorPreferenceListener(jobCancelabilityMonitorOptions));
	}

	@SuppressWarnings("restriction")
	private CancelabilityMonitor.BasicOptionsImpl createCancelabilityMonitorService(IPreferenceStore preferences) {
		CancelabilityMonitor.BasicOptionsImpl cancelabilityMonitorOptions;
		cancelabilityMonitorOptions = new CancelabilityMonitor.BasicOptionsImpl();
		cancelabilityMonitorOptions.setEnabled(true);
		cancelabilityMonitorOptions.setErrorThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getInt(PreferenceConstants.TASK_MONITORING_ERROR_THRESHOLD_MILLIS)));
		cancelabilityMonitorOptions.setWarningThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getInt(PreferenceConstants.TASK_MONITORING_WARNING_THRESHOLD_MILLIS)));
		cancelabilityMonitorOptions.setMaxStackSamples(preferences.getInt(PreferenceConstants.TASK_MONITORING_MAX_STACK_SAMPLES));
		cancelabilityMonitorOptions.setAlwaysReportNonCancelableUserTaskAsError(preferences.getBoolean(PreferenceConstants.TASK_MONITORING_LOG_NON_CANCELLABLE_USER_JOB));
		MonitoringPlugin.getDefault().getBundle().getBundleContext().registerService(CancelabilityMonitor.Options.class, cancelabilityMonitorOptions, null);
		return cancelabilityMonitorOptions;
