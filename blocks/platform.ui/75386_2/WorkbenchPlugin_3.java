
	public CancelabilityMonitor.Options getCancelabilityMonitorOptions() {
		if (cancelabilityMonitorOptionsTracker == null) {
			cancelabilityMonitorOptionsTracker = new ServiceTracker<>(bundleContext,
					CancelabilityMonitor.Options.class.getName(), null);
			cancelabilityMonitorOptionsTracker.open();
		}
		return cancelabilityMonitorOptionsTracker.getService();
	}
