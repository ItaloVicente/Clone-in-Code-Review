		synchronized (jobs) {
			Collection<JobTreeElement> result = new HashSet<>();
			for (Entry<Job, JobInfo> entry : jobs.entrySet()) {
				if (!isCurrentDisplaying(entry.getKey(), debug)) {
					JobInfo jobInfo = entry.getValue();
					GroupInfo group = jobInfo.getGroupInfo();
					if (group == null) {
						result.add(jobInfo);
					} else {
						result.add(group);
					}
				}
