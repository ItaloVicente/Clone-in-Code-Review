	@Override
	protected ConnectorFactory getConnectorFactory() {
		if (Platform.getPreferencesService().getBoolean(Activator.PLUGIN_ID,
				GitCorePreferences.core_sshAgent, true, null)) {
			return super.getConnectorFactory();
		}
		return null;
	}

