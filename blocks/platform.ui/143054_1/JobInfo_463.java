	}

	void beginTask(String taskName, int work) {
		taskInfo = new TaskInfo(this, taskName, work);
	}

	@Override
