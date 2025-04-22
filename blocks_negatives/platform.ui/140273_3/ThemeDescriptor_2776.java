    /**
     * Add a color override to this descriptor.
     *
     * @param definition the definition to add
     */
    void add(ColorDefinition definition) {
    	if (colors.contains(definition)) {
    		colors.remove(definition);
