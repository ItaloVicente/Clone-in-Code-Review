		registerState(srv,
				ToggleBranchHierarchyCommand.ID,
				ToggleBranchHierarchyCommand.TOGGLE_STATE, branchHierarchyMode);
		registerState(srv,
				ToggleTagSortingCommand.ID,
				ToggleTagSortingCommand.TOGGLE_STATE, ascendingTagSorting);
	}

	private void registerState(ICommandService srv, String commandId,
			String stateId, final AtomicBoolean booleanToSet) {
		State state = srv.getCommand(commandId).getState(stateId);
		IStateListener l = new IStateListener() {

			@Override
			public void handleStateChange(State changedState, Object oldValue) {
				setValueFromState(changedState, booleanToSet);
			}
		};
		state.addListener(l);
		commandStates.put(state, l);
		setValueFromState(state, booleanToSet);
	}

	private void setValueFromState(State state, AtomicBoolean booleanToSet) {
