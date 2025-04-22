    /**
     * Sets the value of the property with the given name to the
     * given value, or to <code>null</code> if the property is to be
     * removed. If this viewer has such a property, its value is
     * replaced; otherwise a new property is added.
     * <p>
     * The default implementation records properties in an internal
     * table which is searched linearly. Overriding this method is generally not
     * required if the number of different keys is small. If a more
     * efficient representation of a viewer's properties is required,
     * override both <code>getData</code> and <code>setData</code>.
     * </p>
     *
     * @param key the property name
     * @param value the property value, or <code>null</code> if
     *    the property is not found
     */
    public void setData(String key, Object value) {
        Assert.isNotNull(key);
        /* Remove the key/value pair */
        if (value == null) {
            if (keys == null) {
