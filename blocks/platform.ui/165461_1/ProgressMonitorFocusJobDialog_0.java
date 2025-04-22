		openJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				openJob.removeJobChangeListener(this);
				IStatus result = openJob.getResult();
				if (result != null && result == Status.CANCEL_STATUS) {
					finishedRun();
					cleanUpFinishedJob();
				}
			}
		});
