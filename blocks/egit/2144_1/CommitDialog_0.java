	@SuppressWarnings("restriction")
	private static String getCommitMessageFromCurrentTask() {
		try {
			ITaskActivityManager tam = TasksUi.getTaskActivityManager();
			ITask task = tam.getActiveTask();
			if(task == null)
				return ""; //$NON-NLS-1$

			String template = FocusedTeamUiPlugin.getDefault().getPreferenceStore().getString(
						FocusedTeamUiPlugin.COMMIT_TEMPLATE);
			return FocusedTeamUiPlugin.getDefault().getCommitTemplateManager().generateComment(task, template);
		} catch(Exception e) {
			return ""; //$NON-NLS-1$
		}
	}

