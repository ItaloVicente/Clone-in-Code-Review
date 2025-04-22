        return (FontData[]) result;
    }

    /**
     * Returns the font associated with the given symbolic font name.
     * Returns the default font if there is no special value associated
     * with that name.
     * <p>
     * This method should only be called from the UI thread. If you are not on the UI
     * thread then wrap the call with a
     * <code>PlatformUI.getWorkbench().getDisplay().synchExec()</code> in order to
     * guarantee the correct result. Failure to do this may result in an {@link
     * SWTException} being thrown.
     * </p>
     * @param symbolicName symbolic font name
     * @return the font
     */
    public Font get(String symbolicName) {

        return getFontRecord(symbolicName).getBaseFont();
    }

    /**
     * Returns the bold font associated with the given symbolic font name.
     * Returns the bolded default font if there is no special value associated
     * with that name.
     * <p>
     * This method should only be called from the UI thread. If you are not on the UI
     * thread then wrap the call with a
     * <code>PlatformUI.getWorkbench().getDisplay().synchExec()</code> in order to
     * guarantee the correct result. Failure to do this may result in an {@link
     * SWTException} being thrown.
     * </p>
     * @param symbolicName symbolic font name
     * @return the font
     * @since 3.0
     */
    public Font getBold(String symbolicName) {

        return getFontRecord(symbolicName).getBoldFont();
    }

    /**
     * Returns the italic font associated with the given symbolic font name.
     * Returns the italic default font if there is no special value associated
     * with that name.
     * <p>
     * This method should only be called from the UI thread. If you are not on the UI
     * thread then wrap the call with a
     * <code>PlatformUI.getWorkbench().getDisplay().synchExec()</code> in order to
     * guarantee the correct result. Failure to do this may result in an {@link
     * SWTException} being thrown.
     * </p>
     * @param symbolicName symbolic font name
     * @return the font
     * @since 3.0
     */
    public Font getItalic(String symbolicName) {

        return getFontRecord(symbolicName).getItalicFont();
    }

    /**
     * Return the font record for the key.
     * @param symbolicName The key for the record.
     * @return FontRecord
     */
    private FontRecord getFontRecord(String symbolicName) {
        Assert.isNotNull(symbolicName);
        Object result = stringToFontRecord.get(symbolicName);
        if (result != null) {
