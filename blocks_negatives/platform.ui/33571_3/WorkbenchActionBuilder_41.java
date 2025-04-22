    
    private IContributionItem getTaskItem() {
		return getItem(
				IDEActionFactory.ADD_TASK.getId(),
				IDEActionFactory.ADD_TASK.getCommandId(),
				null, null, IDEWorkbenchMessages.Workbench_addTask,
				IDEWorkbenchMessages.Workbench_addTaskToolTip, null);
