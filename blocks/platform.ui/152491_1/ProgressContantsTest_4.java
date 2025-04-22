	private int countBelongingProgressItems(Object family) {
		int count = 0;
		ProgressInfoItem[] progressInfoItems = progressView.getViewer().getProgressInfoItems();
		for (ProgressInfoItem progressInfoItem : progressInfoItems) {
			JobInfo[] jobInfos = progressInfoItem.getJobInfos();
			for (JobInfo jobInfo : jobInfos) {
				if (jobInfo.getJob().belongsTo(family)) {
					count++;
				}
			}
		}
		return count;
	}
