    /**
     * Sets the current value of the preference with the given name to
     * the given string value without sending a property change.
     * <p>
     * This method does not fire a property change event and
     * should only be used for setting internal preferences
     * that are not meant to be processed by listeners.
     * Normal clients should instead call #setValue.
     * </p>
     *
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
