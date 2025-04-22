	@Override
	public Command defineCommand(String id, String name, String description, Category category,
			IParameter[] parameters, String helpContextId) {
		Command cmd = commandManager.getCommand(id);
		if (!cmd.isDefined()) {
			cmd.define(name, description, category, parameters, null, helpContextId);
			cmd.setHandler(HandlerServiceImpl.getHandler(id));
		}
		return cmd;
	}

