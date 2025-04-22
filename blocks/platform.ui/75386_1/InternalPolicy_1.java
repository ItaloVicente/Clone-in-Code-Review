	private static CancelabilityMonitor.Options cancelabilityMonitorOptions;

	public static CancelabilityMonitor.Options getCancelabilityMonitorOptions() {
		if (cancelabilityMonitorOptions == null) {
			cancelabilityMonitorOptions = new CancelabilityMonitor.BasicOptionsImpl();
			((BasicOptionsImpl) cancelabilityMonitorOptions).setEnabled(false);
		}
		return cancelabilityMonitorOptions;
	}

	public static void setCancelabilityMonitorOptions(CancelabilityMonitor.Options options) {
		cancelabilityMonitorOptions = options;
	}
