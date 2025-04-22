		if (!isNeverDisplaying(info.getJob(), showSystemJobs)) {
			for (IJobProgressManagerListener listener : listeners) {
				if (showSystemJobs)
					listener.refreshJobInfo(info);
				else
					listener.removeJob(info);
