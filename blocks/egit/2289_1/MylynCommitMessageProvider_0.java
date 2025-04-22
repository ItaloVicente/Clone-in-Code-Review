		ITask task = getCurrentTask();
		if (task == null)
			return message;
		boolean checkTaskRepository = true;
		message = ContextChangeSet.getComment(checkTaskRepository, task,
				resources);		
		
