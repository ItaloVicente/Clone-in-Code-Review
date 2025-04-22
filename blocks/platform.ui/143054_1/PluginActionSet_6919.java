	public PluginActionSet(ActionSetDescriptor desc) {
		super();
		this.desc = desc;
	}

	public void addPluginAction(WWinPluginAction action) {
		pluginActions.add(action);
	}

	public IAction[] getPluginActions() {
		IAction result[] = new IAction[pluginActions.size()];
		pluginActions.toArray(result);
		return result;
	}

	@Override
