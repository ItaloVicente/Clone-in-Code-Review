	private String getLockToolbarsText() {
		ECommandService commandService = context.get(ECommandService.class);
		Command command = commandService.getCommand(LOCK_TOOLBAR_CMD_ID);
		State state = command.getState(STATE_ID);
		if ((state != null) && (state.getValue() instanceof Boolean)) {
			boolean enabled = ((Boolean) state.getValue()).booleanValue();
			return (enabled) ? Messages.ToolBarManagerRenderer_UnlockToolbars
					: Messages.ToolBarManagerRenderer_LockToolbars;
		}
		return Messages.ToolBarManagerRenderer_ToggleLockToolbars;
