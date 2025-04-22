			return (int) info.preWork * 100 / info.totalWork;
		}
		return IProgressMonitor.UNKNOWN;
	}

	TaskInfo getTaskInfo() {
		return taskInfo;
	}

	@Override
