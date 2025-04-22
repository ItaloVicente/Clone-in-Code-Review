    /**
     * Creates a new FontDescriptor given the an array of FontData that describes
     * the font.
     *
     * @since 3.1
     *
     * @param data an array of FontData that describes the font (will be passed into
     * the Font's constructor)
     * @return a FontDescriptor that describes the given font
     */
    public static FontDescriptor createFrom(FontData[] data) {
        return new ArrayFontDescriptor(data);
    }
