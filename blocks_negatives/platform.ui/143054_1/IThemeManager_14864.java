    /**
     * Adds a property listener to the manager.  Any events fired by the
     * underlying registries of the current theme will cause an event to be
     * fired.  This event is the same event that was fired by the registry.
     * As such, the "source" attribute of the event will not be this manager,
     * but rather the color or font registry.  Additionally, an event is fired
     * when the current theme changes to a new theme.  The "property" attribute
     * of such an event will have the value {@link IThemeManager#CHANGE_CURRENT_THEME}.
     *
     * @param listener the listener to add
     */
    void addPropertyChangeListener(IPropertyChangeListener listener);
