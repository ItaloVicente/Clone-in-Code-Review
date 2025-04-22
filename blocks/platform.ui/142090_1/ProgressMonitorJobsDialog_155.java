	}

	public void watchTicks() {
		watchTime = System.currentTimeMillis();
	}

	public void createWrapperedMonitor() {
		wrapperedMonitor = new IProgressMonitorWithBlocking() {

			IProgressMonitor superMonitor = ProgressMonitorJobsDialog.super
					.getProgressMonitor();

			@Override
