    }

    /**
     * Set the foreground and background colors of the
     * control to the specified values. If the values are
     * null than ignore them.
     * @param control the control the foreground and/or background color should be set
     *
     * @param foreground Color the foreground color (maybe <code>null</code>)
     * @param background Color the background color (maybe <code>null</code>)
     */
    public static void setColors(Control control, Color foreground,
            Color background) {
        if (foreground != null) {
