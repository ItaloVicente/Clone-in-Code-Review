		Iterator iter = pluginActions.iterator();
		while (iter.hasNext()) {
			WWinPluginAction action = (WWinPluginAction) iter.next();
			action.dispose();
		}
		pluginActions.clear();
		bars = null;
