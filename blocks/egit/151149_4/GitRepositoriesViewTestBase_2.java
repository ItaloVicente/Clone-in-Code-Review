	protected static boolean setLinkWithSelection(boolean state) {
		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(),
				ICommandService.class);
		State linkingState = srv.getCommand(ToggleLinkWithSelectionCommand.ID)
				.getState(RegistryToggleState.STATE_ID);
		boolean previousState = ((Boolean) linkingState.getValue())
				.booleanValue();
		linkingState.setValue(Boolean.valueOf(state));
		return previousState;
	}

