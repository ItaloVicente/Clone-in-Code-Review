	}

	private void readActivityPatternBindingDefinition(IConfigurationElement configurationElement) {
		ActivityPatternBindingDefinition activityPatternBindingDefinition = Persistence
				.readActivityPatternBindingDefinition(new ConfigurationElementMemento(
						configurationElement), getNamespace(configurationElement));

		if (activityPatternBindingDefinition != null) {
			activityPatternBindingDefinitions.add(activityPatternBindingDefinition);
