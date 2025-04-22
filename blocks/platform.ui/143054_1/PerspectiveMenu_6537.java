		if (action == null) {
			IPerspectiveDescriptor descriptor = reg.findPerspectiveWithId(id);
			if (descriptor != null) {
				action = new OpenPerspectiveAction(window, descriptor, this);
				action.setActionDefinitionId(id);
				actions.put(id, action);
			}
		}
		return action;
	}

