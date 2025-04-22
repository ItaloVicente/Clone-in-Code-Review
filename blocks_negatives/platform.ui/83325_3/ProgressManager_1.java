	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InvocationTargetException,
			InterruptedException {
		if (fork == false || cancelable == false) {
			final ProgressMonitorJobsDialog dialog = new ProgressMonitorJobsDialog(
					null);
