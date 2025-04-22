		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(),
				ICommandService.class);
		State commandState = srv.getCommand(ToggleLinkWithSelectionCommand.ID)
				.getState(RegistryToggleState.STATE_ID);
		Boolean followsSelection = (Boolean) commandState.getValue();
		commandState.setValue(Boolean.TRUE);
