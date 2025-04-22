        ICategorizedThemeElementDefinition, IEditable {

    private String label;

    private String id;

    private String defaultsTo;

    private String categoryId;

    private String description;

    private String value;

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
        this.label = fontName;
        this.id = uniqueId;
        this.defaultsTo = defaultsId;
        this.value = value;
        this.categoryId = categoryId;
        this.description = fontDescription;
        this.isEditable = isEditable;
    }

    /**
     * Create a new instance of the receiver.
     * 
     * @param originalFont the original definition.  This will be used to populate 
     * all fields except defaultsTo and value.  defaultsTo will always be 
     * <code>null</code>.
     * @param datas the FontData[] value
     */
    public FontDefinition(FontDefinition originalFont, FontData[] datas) {
        this.label = originalFont.getName();
        this.id = originalFont.getId();
        this.categoryId = originalFont.getCategoryId();
        this.description = originalFont.getDescription();
        this.isEditable = originalFont.isEditable();
        this.parsedValue = datas;
    }

    /**
     * Returns the defaultsTo. This is the id of the text font
     * that this font defualts to.
     * @return String or <pre>null</pre>.
     */
    public String getDefaultsTo() {
        return defaultsTo;
    }

    /**
     * Returns the description.
     * @return String or <pre>null</pre>.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the label.
     * @return String
     */
    public String getName() {
        return label;
    }

    /**
     * Returns the id.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the categoryId.
     * @return String
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Returns the value.
     * 
     * @return FontData []
     */
    public FontData[] getValue() {
        if (value == null) {
