    /**
     * Returns whether this cascading registry has an override for the provided
     * color key.
     *
     * @param colorKey the provided color key
     * @return hether this cascading registry has an override
     */
    public boolean hasOverrideFor(String colorKey) {
        return super.hasValueFor(colorKey);
    }
