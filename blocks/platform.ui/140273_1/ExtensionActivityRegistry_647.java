	}

	private void readActivityDefinition(IConfigurationElement configurationElement) {
		ActivityDefinition activityDefinition = Persistence.readActivityDefinition(
				new ConfigurationElementMemento(configurationElement), getNamespace(configurationElement));

		if (activityDefinition != null) {
