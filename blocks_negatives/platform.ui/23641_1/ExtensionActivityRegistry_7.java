    }

    private void readCategoryActivityBindingDefinition(
            IConfigurationElement configurationElement) {
        CategoryActivityBindingDefinition categoryActivityBindingDefinition = Persistence
                .readCategoryActivityBindingDefinition(
                        new ConfigurationElementMemento(configurationElement),
                        getNamespace(configurationElement));

        if (categoryActivityBindingDefinition != null) {
			categoryActivityBindingDefinitions
                    .add(categoryActivityBindingDefinition);
