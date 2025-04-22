		action.addPropertyChangeListener(propertyListener);
		if (action != null) {
			String commandId = action.getActionDefinitionId();
			ExternalActionManager.ICallback callback = ExternalActionManager.getInstance().getCallback();

			if ((callback != null) && (commandId != null)) {
				callback.addPropertyChangeListener(commandId, actionTextListener);
