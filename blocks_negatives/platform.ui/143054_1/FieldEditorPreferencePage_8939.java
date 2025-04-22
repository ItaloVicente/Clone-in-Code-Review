     * Creates a new field editor preference page with the given style,
     * an empty title, and no image.
     *
     * @param style either <code>GRID</code> or <code>FLAT</code>
     */
    protected FieldEditorPreferencePage(int style) {
        super();
        this.style = style;
    }

    /**
     * Creates a new field editor preference page with the given title
     * and style, but no image.
     *
     * @param title the title of this preference page
     * @param style either <code>GRID</code> or <code>FLAT</code>
     */
    protected FieldEditorPreferencePage(String title, int style) {
        super(title);
        this.style = style;
    }

    /**
     * Creates a new field editor preference page with the given title,
     * image, and style.
     *
     * @param title the title of this preference page
     * @param image the image for this preference page, or
     *   <code>null</code> if none
     * @param style either <code>GRID</code> or <code>FLAT</code>
     */
    protected FieldEditorPreferencePage(String title, ImageDescriptor image,
            int style) {
        super(title, image);
        this.style = style;
    }

    /**
     * Adds the given field editor to this page.
     *
     * @param editor the field editor
     */
    protected void addField(FieldEditor editor) {
        if (fields == null) {
