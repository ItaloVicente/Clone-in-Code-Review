        return (FontDefinition) findDescriptor(getFonts(), id);
    }

    /**
     * @param definition
     */
    void add(ThemeElementCategory definition) {
        if (findCategory(definition.getId()) != null) {
