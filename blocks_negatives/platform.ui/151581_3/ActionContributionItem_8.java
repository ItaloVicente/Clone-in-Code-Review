			ExternalActionManager.IExecuteCallback callback = null;
			String actionDefinitionId = action.getActionDefinitionId();
			if (actionDefinitionId != null) {
				Object obj = ExternalActionManager.getInstance()
						.getCallback();
				if (obj instanceof ExternalActionManager.IExecuteCallback) {
					callback = (ExternalActionManager.IExecuteCallback) obj;
				}
