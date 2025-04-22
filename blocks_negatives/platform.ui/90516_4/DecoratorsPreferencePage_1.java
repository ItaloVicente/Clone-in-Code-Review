        DecoratorManager manager = WorkbenchPlugin.getDefault()
				.getDecoratorManager();
        DecoratorDefinition[] definitions = manager
                .getAllDecoratorDefinitions();
        for (int i = 0; i < definitions.length; i++) {
            checkboxViewer.setChecked(definitions[i], definitions[i]
