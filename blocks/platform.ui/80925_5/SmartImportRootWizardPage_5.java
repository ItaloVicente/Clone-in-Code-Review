	@Override
	public void dispose() {
		if (wizardProgressMonitor != null) {
			wizardProgressMonitor.setCanceled(true);
			wizardProgressMonitor.done();
		}
		super.dispose();
	}
