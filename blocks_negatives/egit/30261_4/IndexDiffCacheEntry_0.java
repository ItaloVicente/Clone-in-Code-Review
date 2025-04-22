		updateJobs.add(job);
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				updateJobs.remove(event.getJob());
			}
		});
		job.schedule();
