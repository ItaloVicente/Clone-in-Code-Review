    /**
     * Creates a FontDescriptor that describes an existing font. The resulting
     * descriptor depends on the original Font, and disposing the original Font
     * while the descriptor is still in use may cause SWT to throw a graphic
     * disposed exception.
     *
     * @since 3.1
     *
     * @param font font to create
     * @return a newly created FontDescriptor that describes the given font
     */
    public static FontDescriptor createFrom(Font font) {
        return new ArrayFontDescriptor(font);
    }
