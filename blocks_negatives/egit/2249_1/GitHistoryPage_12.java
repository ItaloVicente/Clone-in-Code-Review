	private void cancelRefreshJob() {
		if (job != null && job.getState() != Job.NONE) {
			job.cancel();
			try {
				job.join();
			} catch (InterruptedException e) {
				cancelRefreshJob();
				return;
			}
			job = null;
