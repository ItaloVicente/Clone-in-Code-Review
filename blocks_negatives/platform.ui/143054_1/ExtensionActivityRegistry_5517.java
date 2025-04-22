    private void readActivityRequirementBindingDefinition(
            IConfigurationElement configurationElement) {
        ActivityRequirementBindingDefinition activityRequirementBindingDefinition = Persistence
                .readActivityRequirementBindingDefinition(
                        new ConfigurationElementMemento(configurationElement),
                        getNamespace(configurationElement));
