		validatePage();
	}

	@Override
	public void dispose() {
		stopAndDisconnectCurrentWork();
		super.dispose();
	}

	public ProgressMonitorPart getWizardProgressMonitor() {
		return this.wizardProgressMonitor.get();
