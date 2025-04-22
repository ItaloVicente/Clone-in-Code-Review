	}

	private void runAction(String pluginId, String className) {
		Bundle pluginBundle = Platform.getBundle(pluginId);
		if (pluginBundle == null) {
			logActionLinkError(pluginId, className);
			return;
		}
