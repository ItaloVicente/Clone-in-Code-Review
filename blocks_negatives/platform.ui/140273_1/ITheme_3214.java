    /**
     * Adds a property listener to the theme.  Any events fired by the
     * underlying registries will cause an event to be fired.  This event is the
     * same event that was fired by the registry.  As such, the "source"
     * attribute of the event will not be this theme, but rather the color or
     * font registry.
     *
     * @param listener the listener to add
     */
    void addPropertyChangeListener(IPropertyChangeListener listener);
