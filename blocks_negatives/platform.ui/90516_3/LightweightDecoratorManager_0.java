
		LightweightDecoratorDefinition[] decorators = getDecoratorsFor(element);

		for (int i = 0; i < decorators.length; i++) {
			LightweightDecoratorDefinition dd = decorators[i];
			decoration.setCurrentDefinition(dd);
			decorate(element, decoration, dd);
