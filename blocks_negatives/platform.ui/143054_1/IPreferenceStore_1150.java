    /**
     * Sets the current value of the long-valued preference with the
     * given name.
     * <p>
     * A property change event is reported if the current value of the
     * preference actually changes from its previous value. In the event
     * object, the property name is the name of the preference, and the
     * old and new values are wrapped as objects.
     * </p>
     * <p>
     * Note that the preferred way of re-initializing a preference to its
     * default value is to call <code>setToDefault</code>.
     * </p>
     *
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
