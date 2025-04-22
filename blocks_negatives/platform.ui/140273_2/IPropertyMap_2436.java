    /**
     * Returns the value of the given property. Returns null if the given
     * property does not exist, cannot be converted into the expected type,
     * or if the value of the property is null.
     *
     * @param propertyId property ID to query
     * @param propertyType type of the expected return value
     * @return an object of the given propertyType or null if the property
     * does not exist or has the wrong type
     * @since 3.1
     */
    Object getValue(String propertyId, Class propertyType);
