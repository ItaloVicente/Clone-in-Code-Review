
	public String getBranchNameSuggestion() {

		if (getCurrentTask() == null)
			return null;

		String taskKey = getCurrentTask().getTaskKey();
		if (taskKey == null)
			taskKey = getCurrentTask().getTaskId();
		String summary = normalizeSummary(getCurrentTask().getSummary());

		return taskKey + "_" + summary; //$NON-NLS-1$
	}

	private String normalizeSummary(String summary) {
		String normalized;

		normalized = summary
				.replace(" ", "_").replaceAll("\\W", ""); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		if (normalized.length() > 30)
			normalized = normalized.substring(0, 30);

		return normalized;
	}

	public String getDefaultSourceReference() {
		return null;
	}
