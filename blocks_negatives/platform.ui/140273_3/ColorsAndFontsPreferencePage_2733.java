        return getColorValue(ancestor);
    }

    /**
     * Get the RGB value for the specified definition.  Cascades through
     * preferenceToSet, valuesToSet and finally the registry.
     *
     * @param definition the <code>ColorDefinition</code>.
     * @return the <code>RGB</code> value.
     */
    private RGB getColorValue(ColorDefinition definition) {
        String id = definition.getId();
