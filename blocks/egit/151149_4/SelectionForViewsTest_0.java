		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(),
				ICommandService.class);
		linkWithSelectionState = srv
				.getCommand(ToggleLinkWithSelectionCommand.ID)
				.getState(RegistryToggleState.STATE_ID);
		initialLinkingWithSelection = (Boolean) linkWithSelectionState
				.getValue();
		linkWithSelectionState.setValue(Boolean.TRUE);
