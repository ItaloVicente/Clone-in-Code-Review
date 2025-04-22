	public String getBranchNameSuggestion() {
		ITask task = getCurrentTask();
		if (task == null)
			return null;

		String taskKey = task.getTaskKey();
		if (taskKey == null)
			taskKey = task.getTaskId();

		StringBuilder sb = new StringBuilder();
		sb.append(TasksUiInternal.getTaskPrefix(task.getConnectorKind()));
		sb.append(task.getTaskKey());
		sb.append("-"); //$NON-NLS-1$
		sb.append(task.getSummary());
		return normalizeBranchName(sb.toString());
