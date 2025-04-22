        }
        return false;
    }

    /**
     * Converts a String into a FontData object.
     */
    private FontData makeFontData(String value) throws MissingResourceException {
        try {
            return StringConverter.asFontData(value.trim());
        } catch (DataFormatException e) {
            throw new MissingResourceException(
                    "Wrong font data format. Value is: \"" + value + "\"", getClass().getName(), value); //$NON-NLS-2$//$NON-NLS-1$
        }
    }

    /**
     * Adds (or replaces) a font to this font registry under the given
     * symbolic name.
     * <p>
     * A property change event is reported whenever the mapping from
     * a symbolic name to a font changes. The source of the event is
     * this registry; the property name is the symbolic font name.
     * </p>
     *
     * @param symbolicName the symbolic font name
     * @param fontData an Array of FontData
     */
    public void put(String symbolicName, FontData[] fontData) {
        put(symbolicName, fontData, true);
    }

    /**
     * Adds (or replaces) a font to this font registry under the given
     * symbolic name.
     * <p>
     * A property change event is reported whenever the mapping from
     * a symbolic name to a font changes. The source of the event is
     * this registry; the property name is the symbolic font name.
     * </p>
     *
     * @param symbolicName the symbolic font name
     * @param fontData an Array of FontData
     * @param update - fire a font mapping changed if true. False
     * 	if this method is called from the get method as no setting
     *  has changed.
     */
    private void put(String symbolicName, FontData[] fontData, boolean update) {

        Assert.isNotNull(symbolicName);
        Assert.isNotNull(fontData);

        FontData[] existing = stringToFontData.get(symbolicName);
        if (Arrays.equals(existing, fontData)) {
