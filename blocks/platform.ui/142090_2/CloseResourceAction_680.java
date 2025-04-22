		String message;
		if (resources.size() == 1) {
			message = NLS.bind(IDEWorkbenchMessages.CloseResourceAction_warningForOne, resources.get(0).getName());
		} else {
			message = IDEWorkbenchMessages.CloseResourceAction_warningForMultiple;
		}
