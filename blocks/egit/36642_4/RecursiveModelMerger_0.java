	private void waitForScope(IMergeContext context) {
		boolean joined = false;
		while (!joined) {
			try {
				Job.getJobManager().join(context, new NullProgressMonitor());
				joined = true;
			} catch (InterruptedException e) {
			}
		}
	}

