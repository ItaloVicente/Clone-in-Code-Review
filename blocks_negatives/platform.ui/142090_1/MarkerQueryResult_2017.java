    /**
     * Creates a new marker query result with the given values.
     * <p>
     * The values may not be empty.
     * </p>
     *
     * @param markerAttributeValues the target marker's attribute values
     */
    public MarkerQueryResult(String[] markerAttributeValues) {
        if (markerAttributeValues == null) {
            throw new IllegalArgumentException();
        }
        values = markerAttributeValues;
        computeHashCode();
    }
