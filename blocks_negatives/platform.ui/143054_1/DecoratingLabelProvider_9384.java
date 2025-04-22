     * Returns the label decorator, or <code>null</code> if none has been set.
     *
     * @return the label decorator, or <code>null</code> if none has been set.
     */
    public ILabelDecorator getLabelDecorator() {
        return decorator;
    }

    /**
     * Returns the nested label provider.
     *
     * @return the nested label provider
     */
    public ILabelProvider getLabelProvider() {
        return provider;
    }

    /**
     * The <code>DecoratingLabelProvider</code> implementation of this
     * <code>ILabelProvider</code> method returns the text label provided
     * by the nested label provider's <code>getText</code> method,
     * decorated with the decoration provided by the label decorator's
     * <code>decorateText</code> method.
     */
    @Override
