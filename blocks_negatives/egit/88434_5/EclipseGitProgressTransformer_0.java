		totalWork = total;
		task = new SubProgressMonitor(root, 1000);
		if (totalWork == UNKNOWN)
			task.beginTask(EMPTY_STRING, IProgressMonitor.UNKNOWN);
		else
			task.beginTask(EMPTY_STRING, totalWork);
		task.subTask(msg);
