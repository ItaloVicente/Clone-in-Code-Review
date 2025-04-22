    /**
     * <code>IPropertySource2</code> overrides the specification of this <code>IPropertySource</code>
     * method to return <code>true</code> instead of <code>false</code> if the specified
     * property does not have a meaningful default value.
     * <code>isPropertyResettable</code> will only be called if <code>isPropertySet</code> returns
     * <code>true</code>.
     * <p>
     * Returns whether the value of the property with the given id has changed
     * from its default value. Returns <code>false</code> if this source does
     * not have the specified property.
     * </p>
     * <p>
     * If the notion of default value is not meaningful for the specified
     * property then <code>true</code> is returned.
     * </p>
     *
     * @param id
     *            the id of the property
     * @return <code>true</code> if the value of the specified property has
     *         changed from its original default value, <code>true</code> if
     *         the specified property does not have a meaningful default value,
     *         and <code>false</code> if this source does not have the
     *         specified property
     * @see IPropertySource2#isPropertyResettable(Object)
     * @see #resetPropertyValue(Object)
     * @since 3.1
     */
    @Override
