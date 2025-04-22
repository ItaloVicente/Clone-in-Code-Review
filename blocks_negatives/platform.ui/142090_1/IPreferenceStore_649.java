    /**
     * Sets the default value for the float-valued preference with the
     * given name.
     * <p>
     * Note that the current value of the preference is affected if
     * the preference's current value was its old default value, in which
     * case it changes to the new default value. If the preference's current
     * is different from its old default value, its current value is
     * unaffected. No property change events are reported by changing default
     * values.
     * </p>
     *
     * @param name the name of the preference
     * @param value the new default value for the preference
     */
