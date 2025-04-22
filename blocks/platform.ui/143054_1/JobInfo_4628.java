		this.canceled = true;
		this.job.cancel();
		progressManager.refreshJobInfo(this);
	}

	void clearChildren() {
		children.clear();
	}

	void clearTaskInfo() {
