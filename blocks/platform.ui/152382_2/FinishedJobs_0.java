	private void removeDuplicates(JobTreeElement info) {
		synchronized (keptjobinfos) {
			JobTreeElement[] toBeRemoved = findJobsToRemove(info);
			if (toBeRemoved != null) {
				for (JobTreeElement element : toBeRemoved) {
					remove(element);
				}
