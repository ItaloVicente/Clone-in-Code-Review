    private FontData[] data;
    private Font originalFont = null;

    /**
     * Creates a font descriptor for a font with the given name, height,
     * and style. These arguments are passed directly to the constructor
     * of Font.
     *
     * @param data FontData describing the font to create
     *
     * @see org.eclipse.swt.graphics.Font#Font(org.eclipse.swt.graphics.Device, org.eclipse.swt.graphics.FontData)
     * @since 3.1
     */
    public ArrayFontDescriptor(FontData[] data) {
        this.data = data;
    }

    /**
     * Creates a font descriptor that describes the given font.
     *
     * @param originalFont font to be described
     *
     * @see FontDescriptor#createFrom(org.eclipse.swt.graphics.Font)
     * @since 3.1
     */
    public ArrayFontDescriptor(Font originalFont) {
        this(originalFont.getFontData());
        this.originalFont = originalFont;
    }

    @Override
