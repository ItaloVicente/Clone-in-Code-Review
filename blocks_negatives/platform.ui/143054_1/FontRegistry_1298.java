    }

    /**
     * Returns the default font data.  Creates it if necessary.
     * <p>
     * This method should only be called from the UI thread. If you are not on the UI
     * thread then wrap the call with a
     * <code>PlatformUI.getWorkbench().getDisplay().synchExec()</code> in order to
     * guarantee the correct result. Failure to do this may result in an {@link
     * SWTException} being thrown.
     * </p>
     * @return Font
     */
    public Font defaultFont() {
        return defaultFontRecord().getBaseFont();
    }

    /**
     * Returns the font descriptor for the font with the given symbolic
     * font name. Returns the default font if there is no special value
     * associated with that name
     *
     * @param symbolicName symbolic font name
     * @return the font descriptor (never null)
     *
     * @since 3.3
     */
    public FontDescriptor getDescriptor(String symbolicName) {
        Assert.isNotNull(symbolicName);
        return FontDescriptor.createFrom(getFontData(symbolicName));
    }



    /**
     * Returns the default font record.
     */
    private FontRecord defaultFontRecord() {

        FontRecord record = stringToFontRecord
                .get(JFaceResources.DEFAULT_FONT);
        if (record == null) {
            Font defaultFont = calculateDefaultFont();
            record = createFont(JFaceResources.DEFAULT_FONT, defaultFont
                    .getFontData());
            defaultFont.dispose();
            stringToFontRecord.put(JFaceResources.DEFAULT_FONT, record);
        }
        return record;
    }

    /**
     * Returns the default font data.  Creates it if necessary.
     */
    private FontData[] defaultFontData() {
        return defaultFontRecord().baseData;
    }

    /**
     * Returns the font data associated with the given symbolic font name.
     * Returns the default font data if there is no special value associated
     * with that name.
     *
     * @param symbolicName symbolic font name
     * @return the font
     */
    public FontData[] getFontData(String symbolicName) {

        Assert.isNotNull(symbolicName);
        Object result = stringToFontData.get(symbolicName);
        if (result == null) {
