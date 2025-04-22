    /**
     * If this map represents the union of multiple property maps, this
     * returns true iff the property existed in every map in the union.
     * Always returns true if this map was not computed from the union
     * of multiple maps.
     *
     * @param propertyId
     * @return true iff the given property existed in every child map
     * @since 3.1
     */
    public boolean isCommonProperty(String propertyId);
