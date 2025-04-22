		
		final Object jobIsDone = new Object();
		final JobChangeAdapter jobListener = new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				synchronized (jobIsDone) {
					jobIsDone.notify();
				}
			}
		};
		job.addJobChangeListener(jobListener);
		
