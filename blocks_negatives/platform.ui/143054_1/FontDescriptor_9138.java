    /**
     * Creates a new FontDescriptor given the associated FontData
     *
     * @param data FontData describing the font to create
     * @return a newly created FontDescriptor
     */
    public static FontDescriptor createFrom(FontData data) {
        return new ArrayFontDescriptor(new FontData[]{data});
    }
