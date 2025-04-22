		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.addPreferenceChangeListener(preferenceListener);
	}

	void dispose() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.removePreferenceChangeListener(preferenceListener);
