    private boolean isEditable;

    private FontData[] parsedValue;

    /**
     * Create a new instance of the receiver.
     *
     * @param fontName The name display
     * ed in the preference page.
     * @param uniqueId The id used to refer to this definition.
     * @param defaultsId The id of the font this defaults to.
     * @param fontDescription The description of the font in the preference page.
     */
    public FontDefinition(String fontName, String uniqueId, String defaultsId,
            String value, String categoryId, boolean isEditable,
            String fontDescription) {
