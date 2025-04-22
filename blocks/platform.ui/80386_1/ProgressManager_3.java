	JobInfo getOrCreateJobInfo(Job job) {
		return jobs.computeIfAbsent(job, j -> {
			JobInfo info = new JobInfo(job);
			job.setProperty(JOB_INFO, info);
			return info;
		});
