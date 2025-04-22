		return Preferences.getBoolean(IProgressConstants.SHOW_SYSTEM_JOBS);
	}

	boolean isUpdateJob(Job job) {
		return job.equals(updateJob);
	}
