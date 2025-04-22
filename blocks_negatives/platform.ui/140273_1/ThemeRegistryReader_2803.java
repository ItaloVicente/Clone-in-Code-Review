        return new ColorDefinition(name, id, defaultMapping, value, categoryId,
                isEditable, description, element.getDeclaringExtension()
						.getContributor().getName());
    }

    /**
     * Gets the color value, either via the value attribute or from a color
     * factory.
     *
     * @param element the element to check
     * @return the color string
     */
    private String getColorValue(IConfigurationElement element) {
        if (element == null) {
