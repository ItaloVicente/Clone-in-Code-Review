        return stringToRGB.containsKey(colorKey);
    }

    /**
     * Hook a dispose listener on the SWT display.
     */
    private void hookDisplayDispose() {
        display.disposeExec(displayRunnable);
    }

    /**
     * Adds (or replaces) a color to this color registry under the given
     * symbolic name.
     * <p>
     * A property change event is reported whenever the mapping from a symbolic
     * name to a color changes. The source of the event is this registry; the
     * property name is the symbolic color name.
     * </p>
     *
     * @param symbolicName the symbolic color name
     * @param colorData an <code>RGB</code> object
     */
    public void put(String symbolicName, RGB colorData) {
        put(symbolicName, colorData, true);
    }

    /**
     * Adds (or replaces) a color to this color registry under the given
     * symbolic name.
     * <p>
     * A property change event is reported whenever the mapping from a symbolic
     * name to a color changes. The source of the event is this registry; the
     * property name is the symbolic color name.
     * </p>
     *
     * @param symbolicName the symbolic color name
     * @param colorData an <code>RGB</code> object
     * @param update - fire a color mapping changed if true. False if this
     *            method is called from the get method as no setting has
     *            changed.
     */
    private void put(String symbolicName, RGB colorData, boolean update) {

        Assert.isNotNull(symbolicName);
        Assert.isNotNull(colorData);

        RGB existing = stringToRGB.get(symbolicName);
        if (colorData.equals(existing)) {
