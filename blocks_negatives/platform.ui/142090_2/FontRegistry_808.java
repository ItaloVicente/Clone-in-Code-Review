    public FontRegistry(String location, ClassLoader loader)
            throws MissingResourceException {
        Display display = Display.getCurrent();
        Assert.isNotNull(display);
        readResourceBundle(location);

        cleanOnDisplayDisposal = true;
        hookDisplayDispose(display);
    }

    /**
     * Load the FontRegistry using the ClassLoader from the PlatformUI
     * plug-in
     * <p>
     * This method should only be called from the UI thread. If you are not on the UI
     * thread then wrap the call with a
     * <code>PlatformUI.getWorkbench().getDisplay().synchExec()</code> in order to
     * guarantee the correct result. Failure to do this may result in an {@link
     * SWTException} being thrown.
     * </p>
     * @param location the location to read the resource bundle from
     * @throws MissingResourceException Thrown if a resource is missing
     */
    public FontRegistry(String location) throws MissingResourceException {
        this(location, null);
    }

    /**
     * Read the resource bundle at location. Look for a file with the
     * extension _os_ws first, then _os then just the name.
     * @param location - String - the location of the file.
     */

    private void readResourceBundle(String location) {
        String wsname = Util.getWS();
        osname = StringConverter.removeWhiteSpaces(osname).toLowerCase();
        wsname = StringConverter.removeWhiteSpaces(wsname).toLowerCase();
        String OSLocation = location;
        String WSLocation = location;
        ResourceBundle bundle = null;
        if (osname != null) {
            if (wsname != null) {
