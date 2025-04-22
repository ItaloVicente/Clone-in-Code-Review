            }
        }
        return fontData;
    }

    /**
     * Reads the supplied string and returns its corresponding
     * FontData. If it cannot be read then the default FontData
     * will be returned.
     *
     * @param fontDataValue the string value for the font data
     * @return the font data
     */
    public static FontData[] readFontData(String fontDataValue) {
        return basicGetFontData(fontDataValue);
    }

    /**
     * Helper method to construct a point from the given string.
     * @param value
     * @return Point
     */
    private static Point basicGetPoint(String value) {
        Point dp = new Point(POINT_DEFAULT_DEFAULT.x, POINT_DEFAULT_DEFAULT.y);
        if (IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
