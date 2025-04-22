		super.subTask(name);
		runEventLoop();
	}

	@Override
	public void worked(int work) {
		super.worked(work);
		runEventLoop();
	}

	protected String getTaskName() {
		return taskName;
	}
