    /**
     * When a listener is attached to this handler, then this registers a
     * listener with the underlying action.
     *
     * @since 3.1
     */
    private final void attachListener() {
        if (propertyChangeListener == null) {
            attributeValuesByName = getAttributeValuesByNameFromAction();
