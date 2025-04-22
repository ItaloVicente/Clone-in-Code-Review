		validatePage();
	}

	@Override
	public void dispose() {
		stopAndDisconnectCurrentWork();
		getStopButton(wizardProgressMonitor.get()).removeSelectionListener(this.cancelWorkListener);
		super.dispose();
	}

	public ProgressMonitorPart getWizardProgressMonitor() {
		return this.wizardProgressMonitor.get();
