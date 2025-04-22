        return themeRegistry.findColor(defaultsTo);
    }

    /**
     * Get the RGB value of the given colors ancestor, if any.
     *
     * @param definition the descendant <code>ColorDefinition</code>.
     * @return the ancestor <code>RGB</code>, or <code>null</code> if none.
     */
    private RGB getColorAncestorValue(ColorDefinition definition) {
        ColorDefinition ancestor = getColorAncestor(definition);
        if (ancestor == null)
