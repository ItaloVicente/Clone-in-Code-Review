	protected ITask getCurrentTask() {
		return TasksUi.getTaskActivityManager().getActiveTask();
	}

	protected IInteractionContext getActiveContext() {
		return ContextCore.getContextManager().getActiveContext();
	}
