    /**
     * Returns the value of the property with the given name,
     * or <code>null</code> if the property is not found.
     * <p>
     * The default implementation performs a (linear) search of
     * an internal table. Overriding this method is generally not
     * required if the number of different keys is small. If a more
     * efficient representation of a viewer's properties is required,
     * override both <code>getData</code> and <code>setData</code>.
     * </p>
     *
     * @param key the property name
     * @return the property value, or <code>null</code> if
     *    the property is not found
     */
    public Object getData(String key) {
        Assert.isNotNull(key);
        if (keys == null) {
