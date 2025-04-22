		for (int i = 0; i < definitions.length; i++) {
			if (definitions[i].getId().equals(
					"org.eclipse.ui.tests.adaptable.decorator"))
				fullDefinition = definitions[i];
			if (definitions[i].getId().equals(
					"org.eclipse.ui.tests.decorators.lightweightdecorator"))
				lightDefinition = definitions[i];
