	static {
		try {
			ICommandService srv = CommonUtils.getService(
					PlatformUI.getWorkbench(), ICommandService.class);
			State currentToggleState = srv
					.getCommand(ToggleTagSortingCommand.ID)
					.getState(ToggleTagSortingCommand.TOGGLE_STATE);
			setSortTagsAscending(currentToggleState);
			currentToggleState.addListener(
					(state, oldValue) -> setSortTagsAscending(state));
		} catch (Exception e) {
			Activator.logError(
					"exception while restoring tag sort toggle state", e); //$NON-NLS-1$
		}
	}

	private static final void setSortTagsAscending(State toggleState) {
		if (toggleState != null) {
			Object value = toggleState.getValue();
			if (value instanceof Boolean) {
				sortTagsAscending = ((Boolean) value).booleanValue();
			}
		}
	}

