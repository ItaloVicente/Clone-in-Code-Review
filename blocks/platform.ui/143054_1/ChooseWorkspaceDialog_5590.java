	public ChooseWorkspaceDialog(Shell parentShell,
			ChooseWorkspaceData launchData, boolean suppressAskAgain, boolean centerOnMonitor) {
		super(parentShell);
		this.launchData = launchData;
		this.suppressAskAgain = suppressAskAgain;
		this.centerOnMonitor = centerOnMonitor;
	}

	public void prompt(boolean force) {
		if (force || launchData.getShowDialog()) {
			open();
