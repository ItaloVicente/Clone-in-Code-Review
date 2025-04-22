	private final State verboseBranchModeState;

	private boolean verboseBranchMode = false;

	public RepositoriesViewLabelProvider() {
		ICommandService srv = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		verboseBranchModeState = srv.getCommand(ToggleBranchCommitCommand.ID)
				.getState(ToggleBranchCommitCommand.TOGGLE_STATE);
		verboseBranchModeState.addListener(this);
		try {
			this.verboseBranchMode = ((Boolean) verboseBranchModeState
					.getValue()).booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

