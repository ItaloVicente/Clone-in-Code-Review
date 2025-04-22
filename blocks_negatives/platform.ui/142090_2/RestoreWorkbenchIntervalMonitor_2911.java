    /**
     * The default behavior is to create a workbench that can be restored later.  This
     * constructor starts that behavior by setting a flag that will be checked in the
     * appropriate methods.
     */
    public RestoreWorkbenchIntervalMonitor(PerformanceMeter startupMeter, PerformanceMeter shutdownMeter, boolean createRestorableWorkbench) {
        super(2);
        this.startupMeter = startupMeter;
        this.shutdownMeter = shutdownMeter;
        this.createRestorableWorkbench = createRestorableWorkbench;
    }
