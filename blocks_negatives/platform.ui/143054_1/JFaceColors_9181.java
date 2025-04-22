    /**
     * @param display the display the color is from
     * @return the Color used for banner backgrounds
     * @see SWT#COLOR_LIST_BACKGROUND
     * @see Display#getSystemColor(int)
     */
    public static Color getBannerBackground(Display display) {
        return display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    }

    /**
     * @param display the display the color is from
     * @return the Color used for banner foregrounds
     * @see SWT#COLOR_LIST_FOREGROUND
     * @see Display#getSystemColor(int)
     */
    public static Color getBannerForeground(Display display) {
        return display.getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }

    /**
     * @param display the display the color is from
     * @return the background Color for widgets that display errors.
     * @see SWT#COLOR_WIDGET_BACKGROUND
     * @see Display#getSystemColor(int)
     */
    public static Color getErrorBackground(Display display) {
        return display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    }

    /**
     * @param display the display the color is from
     * @return the border Color for widgets that display errors.
     * @see SWT#COLOR_WIDGET_DARK_SHADOW
     * @see Display#getSystemColor(int)
     */
    public static Color getErrorBorder(Display display) {
        return display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
    }

    /**
     * @param display the display the color is from
     * @return the default color to use for displaying errors.
     * @see ColorRegistry#get(String)
     * @see JFacePreferences#ERROR_COLOR
     */
    public static Color getErrorText(Display display) {
        return JFaceResources.getColorRegistry().get(
                JFacePreferences.ERROR_COLOR);
    }

    /**
