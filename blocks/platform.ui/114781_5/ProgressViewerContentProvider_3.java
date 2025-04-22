	public void startListening() {
		ProgressViewUpdater.getSingleton().addCollector(this);
		if (keptJobListener != null) {
			FinishedJobs.getInstance().addListener(keptJobListener);
		}
		if (refreshNeeded) {
			refreshNeeded = false;
			refresh();
		}
	}
