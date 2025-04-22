	public RestoreWorkbenchIntervalMonitor(PerformanceMeter startupMeter, PerformanceMeter shutdownMeter, boolean createRestorableWorkbench) {
		super(2);
		this.startupMeter = startupMeter;
		this.shutdownMeter = shutdownMeter;
		this.createRestorableWorkbench = createRestorableWorkbench;
	}
