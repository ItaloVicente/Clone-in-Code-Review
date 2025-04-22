        return color;
    }

    /**
     * Helper method to construct a <code>FontData</code> from the given string.
     * String is in the form FontData;FontData; in order that
     * multiple FontDatas can be defined.
     * @param value the identifier for the font
     * @return FontData[]
     *
     * @since 3.0
     */
    public static FontData[] basicGetFontData(String value) {
