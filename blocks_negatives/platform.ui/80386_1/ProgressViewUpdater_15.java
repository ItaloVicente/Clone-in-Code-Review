			boolean scheduleUpdate = false;
			synchronized (updateScheduled) {
				if (!updateScheduled.value || updateJob.getState() == Job.NONE) {
					updateScheduled.value = scheduleUpdate = true;
				}
        	}
			if (scheduleUpdate)
				updateJob.schedule(100);
        }
    }

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
