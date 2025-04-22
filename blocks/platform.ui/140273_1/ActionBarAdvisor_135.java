	protected void disposeActions() {
		for (Iterator i = actions.values().iterator(); i.hasNext();) {
			IAction action = (IAction) i.next();
			disposeAction(action);
		}
		actions.clear();
	}
