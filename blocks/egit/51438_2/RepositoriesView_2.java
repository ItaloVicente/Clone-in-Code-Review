		schedule(job, delay);

		scheduledJob = job;
		return scheduledJob;
	}

	private void schedule(Job job, long delay) {
