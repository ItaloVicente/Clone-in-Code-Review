		registerConfiguredRepositoriesListener();
	}

	private void registerConfiguredRepositoriesListener() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.addPreferenceChangeListener(preferenceListener);
