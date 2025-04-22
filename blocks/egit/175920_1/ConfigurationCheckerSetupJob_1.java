	public ConfigurationCheckerSetupJob() {
		super(UIText.ConfigurationChecker_checkConfiguration);
		setSystem(true);
		setUser(false);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (PlatformUI.isWorkbenchRunning()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					check();
