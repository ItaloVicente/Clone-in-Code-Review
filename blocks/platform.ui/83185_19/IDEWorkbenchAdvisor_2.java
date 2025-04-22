		workbenchAdvisor = null;
	}

	private boolean isWorkspaceLocked(IWorkspace workspace) {
		ISchedulingRule currentRule = Job.getJobManager().currentRule();
		return currentRule != null && currentRule.isConflicting(workspace.getRoot());
