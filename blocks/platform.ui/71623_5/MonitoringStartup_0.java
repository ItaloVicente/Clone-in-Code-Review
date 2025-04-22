
		JobCancelabilityMonitor.BasicOptionsImpl jobCancelabilityMonitorOptions = null;
		if (preferences.getBoolean(PreferenceConstants.JOB_MONITORING_ENABLED)) {
			jobCancelabilityMonitorOptions = createJobCancellabilityMonitorService(preferences);
		}

		preferences.addPropertyChangeListener(new JobCancelabilityMonitorPreferenceListener(jobCancelabilityMonitorOptions));
	}

	@SuppressWarnings("restriction")
	private JobCancelabilityMonitor.BasicOptionsImpl createJobCancellabilityMonitorService(IPreferenceStore preferences) {
		JobCancelabilityMonitor.BasicOptionsImpl jobCancelabilityMonitorOptions;
		jobCancelabilityMonitorOptions = new JobCancelabilityMonitor.BasicOptionsImpl();
		jobCancelabilityMonitorOptions.setEnabled(true);
		jobCancelabilityMonitorOptions.setErrorThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getInt(PreferenceConstants.JOB_MONITORING_ERROR_THRESHOLD_MILLIS)));
		jobCancelabilityMonitorOptions.setWarningThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getInt(PreferenceConstants.JOB_MONITORING_WARNING_THRESHOLD_MILLIS)));
		jobCancelabilityMonitorOptions.setMaxStackSamples(preferences.getInt(PreferenceConstants.JOB_MONITORING_MAX_STACK_SAMPLES));
		jobCancelabilityMonitorOptions.setAlwaysReportNonCancelableUserJobAsError(preferences.getBoolean(PreferenceConstants.JOB_MONITORING_LOG_NON_CANCELLABLE_USER_JOB));
		MonitoringPlugin.getDefault().getBundle().getBundleContext().registerService(JobCancelabilityMonitor.Options.class, jobCancelabilityMonitorOptions, null);
		return jobCancelabilityMonitorOptions;
