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
	}

	private String normalizeBranchName(String name) {
		String normalized = name.replaceAll("\\s+", "_").replaceAll("[^\\w-]", ""); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		if (normalized.length() > 30)
			normalized = normalized.substring(0, 30);
		return normalized;
