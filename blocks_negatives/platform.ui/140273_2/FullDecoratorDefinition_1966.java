    /**
     * Create a new instance of the receiver with the
     * supplied values.
     */

    FullDecoratorDefinition(String identifier, IConfigurationElement element) {
        super(identifier, element);
    }

    /**
     * Gets the decorator and creates it if it does
     * not exist yet. Throws a CoreException if there is a problem
     * creating the decorator.
     * This method should not be called unless a check for
     * enabled to be true is done first.
     * @return Returns a ILabelDecorator
     */
    protected ILabelDecorator internalGetDecorator() throws CoreException {
        if (labelProviderCreationFailed) {
