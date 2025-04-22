    /**
     * Return whether or not the font in the parent is the size of a regular
     * font.  Typically used to know if a font is smaller than the High Contrast
     * Font. This method is used to make layout decisions based on screen space.
     *
     * @param parent The Composite whose Font will be queried.
     * @return boolean. True if there are more than 50 lines of possible
     * text in the display.
     */
    public static boolean inRegularFontMode(Composite parent) {
