		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.addPreferenceChangeListener(configuredRepositoriesListener);
	}

	void dispose() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.removePreferenceChangeListener(configuredRepositoriesListener);
