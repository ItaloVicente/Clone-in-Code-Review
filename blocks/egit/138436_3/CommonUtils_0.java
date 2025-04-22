	static {
		try {
			ICommandService srv = CommonUtils.getService(
					PlatformUI.getWorkbench(), ICommandService.class);
			State currentToggleState = srv
					.getCommand(ToggleTagSortingCommand.ID)
					.getState(ToggleTagSortingCommand.TOGGLE_STATE);
			sortTagsAscending = ((Boolean) currentToggleState.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.logError(
					"exception while restoring tag sort toggle state", e); //$NON-NLS-1$
		}
	}

