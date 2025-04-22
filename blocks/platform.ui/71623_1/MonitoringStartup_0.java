
		if (preferences.getBoolean(PreferenceConstants.JOB_MONITORING_ENABLED)) {
			monitorCancellationMonitoringPolicy = new MonitorCancellationMonitoringPolicy();
			monitorCancellationMonitoringPolicy.setEnabled(true);
			monitorCancellationMonitoringPolicy.setWarningThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getLong(PreferenceConstants.JOB_MONITORING_WARNING_THRESHOLD_MILLIS)));
			monitorCancellationMonitoringPolicy.setErrorThreshold(TimeUnit.MILLISECONDS.toNanos(preferences.getLong(PreferenceConstants.JOB_MONITORING_ERROR_THRESHOLD_MILLIS)));
			monitorCancellationMonitoringPolicy.setMaxStackSamples(preferences.getInt(PreferenceConstants.JOB_MONITORING_MAX_STACK_SAMPLES));
			monitorCancellationMonitoringPolicy.setAlwaysReportNonCancellableUserJobAsError(preferences.getBoolean(PreferenceConstants.JOB_MONITORING_LOG_NON_CANCELLABLE_USER_JOB));
			monitorCancellationMonitoringPolicy.setDoNotReportNonCancellableFastSystemJob(preferences.getBoolean(PreferenceConstants.JOB_MONITORING_DO_NOT_LOG_FAST_SYSTEM_JOB));
			MonitoringPlugin.getDefault().getBundle().getBundleContext().registerService(JobManager.MonitorCancellationMonitoringPolicy.class, monitorCancellationMonitoringPolicy, null);
		}

		preferences.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String changedProperty = event.getProperty();
				if (PreferenceConstants.JOB_MONITORING_ENABLED.equals(changedProperty)) {
					if (monitorCancellationMonitoringPolicy == null) {
						monitorCancellationMonitoringPolicy = new MonitorCancellationMonitoringPolicy();
						MonitoringPlugin.getDefault().getBundle().getBundleContext().registerService(JobManager.MonitorCancellationMonitoringPolicy.class, monitorCancellationMonitoringPolicy, null);
					}
					monitorCancellationMonitoringPolicy.setEnabled((Boolean) event.getNewValue());
				} else if (PreferenceConstants.JOB_MONITORING_WARNING_THRESHOLD_MILLIS.equals(changedProperty)) {
					monitorCancellationMonitoringPolicy.setWarningThreshold(TimeUnit.MILLISECONDS.toNanos((Integer) event.getNewValue()));
				} else if (PreferenceConstants.JOB_MONITORING_ERROR_THRESHOLD_MILLIS.equals(changedProperty)) {
					monitorCancellationMonitoringPolicy.setErrorThreshold(TimeUnit.MILLISECONDS.toNanos((Integer) event.getNewValue()));
				} else if (PreferenceConstants.JOB_MONITORING_MAX_STACK_SAMPLES.equals(changedProperty)) {
					monitorCancellationMonitoringPolicy.setMaxStackSamples((Integer) event.getNewValue());
				} else if (PreferenceConstants.JOB_MONITORING_LOG_NON_CANCELLABLE_USER_JOB.equals(changedProperty)) {
					monitorCancellationMonitoringPolicy.setAlwaysReportNonCancellableUserJobAsError((Boolean) event.getNewValue());
				} else if (PreferenceConstants.JOB_MONITORING_DO_NOT_LOG_FAST_SYSTEM_JOB.equals(changedProperty)) {
					monitorCancellationMonitoringPolicy.setDoNotReportNonCancellableFastSystemJob((Boolean) event.getNewValue());
				}
			}
		});
	}

	@SuppressWarnings("restriction")
	static class MonitorCancellationMonitoringPolicy implements JobManager.MonitorCancellationMonitoringPolicy {

		private boolean enabled;
		private long errorThreshold;
		private long warningThreshold;
		private int maxStackSamples;
		private boolean alwaysReportNonCancellableUserJobAsError;
		private boolean doNotReportNonCancellableFastSystemJob;

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public void setErrorThreshold(long errorThreshold) {
			this.errorThreshold = errorThreshold;
		}

		public void setWarningThreshold(long warningThreshold) {
			this.warningThreshold = warningThreshold;
		}

		public void setMaxStackSamples(int maxStackSamples) {
			this.maxStackSamples = maxStackSamples;
		}

		public void setAlwaysReportNonCancellableUserJobAsError(boolean alwaysReportNonCancellableUserJobAsError) {
			this.alwaysReportNonCancellableUserJobAsError = alwaysReportNonCancellableUserJobAsError;
		}

		public void setDoNotReportNonCancellableFastSystemJob(boolean doNotReportNonCancellableFastSystemJob) {
			this.doNotReportNonCancellableFastSystemJob = doNotReportNonCancellableFastSystemJob;
		}

		@Override
		public boolean enabled() {
			return enabled;
		}

		@Override
		public long errorThreshold() {
			return errorThreshold;
		}

		@Override
		public long warningThreshold() {
			return warningThreshold;
		}

		@Override
		public int maxStackSamples() {
			return maxStackSamples;
		}

		@Override
		public boolean alwaysReportNonCancellableUserJobAsError() {
			return alwaysReportNonCancellableUserJobAsError;
		}

		@Override
		public boolean doNotReportNonCancellableFastSystemJob() {
			return doNotReportNonCancellableFastSystemJob;
		}

