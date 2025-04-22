			FinishedJobs finishedJobs) {
		this.job = enclosingJob;
		this.progressManager = progressManager;
		this.finishedJobs = finishedJobs;
	}

	void addSubTask(String subTaskName) {
		children.add(new SubTaskInfo(this, subTaskName));
	}

	void addWork(double workIncrement) {
		if (taskInfo == null) {
