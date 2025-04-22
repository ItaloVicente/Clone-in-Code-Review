    public void addHandlerListener(IHandlerListener handlerListener) {
        if (!hasListeners()) {
            attachListener();
        }

        super.addHandlerListener(handlerListener);
    }

    /**
     * When a listener is attached to this handler, then this registers a
     * listener with the underlying action.
     *
     * @since 3.1
     */
    private void attachListener() {
        if (propertyChangeListener == null) {
            attributeValuesByName = getAttributeValuesByNameFromAction();
