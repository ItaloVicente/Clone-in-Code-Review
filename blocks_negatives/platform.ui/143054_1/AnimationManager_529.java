    /**
     * Get the background color to be used.
     *
     * @param control
     *            The source of the display.
     * @return Color
     */
    static Color getItemBackgroundColor(Control control) {
        return control.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
    }
