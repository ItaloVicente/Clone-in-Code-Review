    /**
     * Creates a decorating label provider which uses the given label decorator
     * to decorate labels provided by the given label provider.
     *
     * @param provider the nested label provider
     * @param decorator the label decorator, or <code>null</code> if no decorator is to be used initially
     */
    public DecoratingLabelProvider(ILabelProvider provider,
            ILabelDecorator decorator) {
        Assert.isNotNull(provider);
        this.provider = provider;
        this.decorator = decorator;
    }

    /**
     * The <code>DecoratingLabelProvider</code> implementation of this <code>IBaseLabelProvider</code> method
     * adds the listener to both the nested label provider and the label decorator.
     *
     * @param listener a label provider listener
     */
    @Override
