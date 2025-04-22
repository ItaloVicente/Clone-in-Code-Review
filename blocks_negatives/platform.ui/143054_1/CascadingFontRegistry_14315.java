    /**
     * Returns whether this cascading registry has an override for the provided
     * color key.
     *
     * @param fontKey the provided color key
     * @return hether this cascading registry has an override
     */
    public boolean hasOverrideFor(String fontKey) {
        return super.hasValueFor(fontKey);
    }
