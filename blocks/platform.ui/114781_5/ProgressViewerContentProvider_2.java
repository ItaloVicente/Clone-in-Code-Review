	public void stopListening() {
		ProgressViewUpdater.getSingleton().removeCollector(this);
		if (keptJobListener != null) {
			FinishedJobs.getInstance().removeListener(keptJobListener);
		}
		refreshNeeded = true;
	}
