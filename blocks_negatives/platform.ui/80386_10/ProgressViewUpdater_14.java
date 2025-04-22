    /**
     * Create the update job that handles the updatesInfo.
     */
    private void createUpdateJob() {
        updateJob = new WorkbenchJob(ProgressMessages.ProgressContentProvider_UpdateProgressJob) {
            @Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				synchronized (updateScheduled) {
					updateScheduled.value = false;
				}
				if (collectors.length == 0) {
					return Status.CANCEL_STATUS;
				}
