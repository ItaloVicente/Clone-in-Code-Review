
	FullDecoratorDefinition(String identifier, IConfigurationElement element) {
		super(identifier, element);
	}

	protected ILabelDecorator internalGetDecorator() throws CoreException {
		if (labelProviderCreationFailed) {
