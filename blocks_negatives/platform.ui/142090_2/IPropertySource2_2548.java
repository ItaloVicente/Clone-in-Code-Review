    /**
     * Returns whether the value of the property with the specified id is
     * resettable to a default value.
     *
     * @param id
     *            the id of the property
     * @return <code>true</code> if the property with the specified id has a
     *         meaningful default value to which it can be resetted, and
     *         <code>false</code> otherwise
     * @see IPropertySource#resetPropertyValue(Object)
     * @see IPropertySource#isPropertySet(Object)
     */
    boolean isPropertyResettable(Object id);
