    /**
     * When no more listeners are registered, then this is used to removed the
     * property change listener from the underlying action.
     *
     * @since 3.1
     *
     */
    private final void detachListener() {
        this.action.removePropertyChangeListener(propertyChangeListener);
        propertyChangeListener = null;
        attributeValuesByName = null;
    }
