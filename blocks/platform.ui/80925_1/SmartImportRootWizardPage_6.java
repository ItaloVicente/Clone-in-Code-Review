	@Override
	public void dispose() {
		if (currentProgressMonitor != null) {
			currentProgressMonitor.setCanceled(true);
			wizardProgressMonitor.done();
		}
		super.dispose();
	}
