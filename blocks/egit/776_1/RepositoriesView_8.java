	@Override
	public void dispose() {
		if (this.scheduledJob != null) {
			this.scheduledJob.cancel();
			this.scheduledJob = null;
		}
		if (this.autoRefreshJob != null) {
			this.autoRefreshJob.cancel();
			this.autoRefreshJob = null;
