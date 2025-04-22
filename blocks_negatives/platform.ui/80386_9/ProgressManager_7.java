		JobInfo info = internalGetJobInfo(job);
		if (info == null) {
			info = new JobInfo(job);
			jobs.put(job, info);
		}
		return info;
