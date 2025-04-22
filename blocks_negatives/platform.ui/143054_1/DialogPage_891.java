        return Dialog.convertVerticalDLUsToPixels(fontMetrics, dlus);
    }

    /**
     * Returns the number of pixels corresponding to the width of the given
     * number of characters.
     * <p>
     * This method may only be called after <code>initializeDialogUnits</code>
     * has been called.
     * </p>
     * <p>
     * Clients may call this framework method, but should not override it.
     * </p>
     *
     * @param chars
     *            the number of characters
     * @return the number of pixels
     */
    protected int convertWidthInCharsToPixels(int chars) {
        if (fontMetrics == null) {
