        return Dialog.convertHorizontalDLUsToPixels(fontMetrics, dlus);
    }

    /**
     * Returns the number of pixels corresponding to the given number of
     * vertical dialog units.
     * <p>
     * This method may only be called after <code>initializeDialogUnits</code>
     * has been called.
     * </p>
     * <p>
     * Clients may call this framework method, but should not override it.
     * </p>
     *
     * @param dlus
     *            the number of vertical dialog units
     * @return the number of pixels
     */
    protected int convertVerticalDLUsToPixels(int dlus) {
        if (fontMetrics == null) {
