
		final Object jobIsDone = new Object();
		final JobChangeAdapter jobListener = new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				synchronized (jobIsDone) {
					jobIsDone.notify();
				}
			}
		};
		job.addJobChangeListener(jobListener);

