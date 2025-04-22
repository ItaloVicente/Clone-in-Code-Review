    /**
     * Fires a property change event corresponding to a change to the
     * current value of the preference with the given name.
     * <p>
     * This method is provided on this interface to simplify the implementation
     * of decorators. There is normally no need to call this method since
     * <code>setValue</code> and <code>setToDefault</code> report such
     * events in due course. Implementations should funnel all preference
     * changes through this method.
     * </p>
     *
     * @param name the name of the preference, to be used as the property
     *  in the event object
     * @param oldValue the old value
     * @param newValue the new value
     */
