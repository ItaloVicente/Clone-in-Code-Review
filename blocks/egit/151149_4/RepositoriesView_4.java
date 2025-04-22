		reactOnSelection = csrv.getCommand(ToggleLinkWithSelectionCommand.ID)
				.getState(RegistryToggleState.STATE_ID);
		reactOnSelection.addListener(reactOnSelectionListener);
		branchHierarchy = csrv.getCommand(ToggleBranchHierarchyCommand.ID)
				.getState(RegistryToggleState.STATE_ID);
		branchHierarchy.addListener(stateChangeListener);
