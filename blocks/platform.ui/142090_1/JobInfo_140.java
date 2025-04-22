		return parent;
	}

	int getPercentDone() {
		TaskInfo info = getTaskInfo();
		if (info != null){
			if(info.totalWork == IProgressMonitor.UNKNOWN) {
