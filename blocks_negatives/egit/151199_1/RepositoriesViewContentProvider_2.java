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
