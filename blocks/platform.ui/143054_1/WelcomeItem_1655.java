		IAction action;
		try {
			actionClass = pluginBundle.loadClass(className);
		} catch (ClassNotFoundException e) {
			logActionLinkError(pluginId, className);
			return;
		}
		try {
