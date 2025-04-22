	public final boolean isEnabled() {
		ICommandService srv = (ICommandService) part.getSite().getService(
				ICommandService.class);
		Command command = srv.getCommand(commandId);
		return command.isEnabled();
	}
