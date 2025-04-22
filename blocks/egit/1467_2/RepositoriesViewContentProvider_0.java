	private final State commandState;

	private boolean branchHierarchyMode = false;

	public RepositoriesViewContentProvider() {
		ICommandService srv = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		commandState = srv.getCommand(
				ToggleBranchHierarchyCommand.ID)
				.getState(ToggleBranchHierarchyCommand.TOGGLE_STATE);
		commandState.addListener(this);
		try {
			this.branchHierarchyMode = ((Boolean) commandState.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}

