		synchronized (jobs) {
			Collection<JobInfo> result = new ArrayList<>();
			for (Entry<Job, JobInfo> entry : jobs.entrySet()) {
				if (!isCurrentDisplaying(entry.getKey(), debug)) {
					result.add(entry.getValue());
				}
			}
			JobInfo[] infos = new JobInfo[result.size()];
			result.toArray(infos);
			return infos;
		}
