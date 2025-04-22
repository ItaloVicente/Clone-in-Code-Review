	protected ActionFactory(String actionId, String commandId) {
		this.actionId = actionId;
		this.commandId = commandId;
	}

	public abstract IWorkbenchAction create(IWorkbenchWindow window);

	public String getId() {
		return actionId;
	}
