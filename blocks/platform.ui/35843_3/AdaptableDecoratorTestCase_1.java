		for (DecoratorDefinition definition : definitions) {
			if (definition.getId().equals(
					"org.eclipse.ui.tests.adaptable.decorator")) {
				fullDefinition = definition;
			}
			if (definition.getId().equals(
					"org.eclipse.ui.tests.decorators.lightweightdecorator")) {
				lightDefinition = definition;
			}
