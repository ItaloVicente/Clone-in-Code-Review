	protected static void setVerboseBranchMode(boolean state) {
		ICommandService srv = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		State verboseBranchModeState = srv.getCommand(
				ToggleBranchCommitCommand.ID).getState(
				ToggleBranchCommitCommand.TOGGLE_STATE);
		verboseBranchModeState.setValue(Boolean.valueOf(state));
	}

