    private void readCategoryDefinition(
            IConfigurationElement configurationElement) {
        CategoryDefinition categoryDefinition = Persistence
                .readCategoryDefinition(new ConfigurationElementMemento(
                        configurationElement),
                        getNamespace(configurationElement));
