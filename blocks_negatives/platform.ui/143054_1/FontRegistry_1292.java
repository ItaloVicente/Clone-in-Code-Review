    /**
     * Creates an empty font registry.
     * <p>
     * There must be an SWT Display created in the current
     * thread before calling this method.
     * </p>
     */
    public FontRegistry() {
    	this(Display.getCurrent(), true);
    }

    /**
     * Creates a font registry and initializes its content from
     * a property file.
     * <p>
     * There must be an SWT Display created in the current
     * thread before calling this method.
     * </p>
     * <p>
     * The OS name (retrieved using <code>System.getProperty("os.name")</code>)
     * is converted to lowercase, purged of whitespace, and appended
     * as suffix (separated by an underscore <code>'_'</code>) to the given
     * location string to yield the base name of a resource bundle
     * acceptable to <code>ResourceBundle.getBundle</code>.
     * The standard Java resource bundle mechanism is then used to locate
     * and open the appropriate properties file, taking into account
     * locale specific variations.
     * </p>
     * <p>
     * For example, on the Windows 2000 operating system the location string
     * <code>"com.example.myapp.Fonts"</code> yields the base name
     * <code>"com.example.myapp.Fonts_windows2000"</code>. For the US English locale,
     * this further elaborates to the resource bundle name
     * <code>"com.example.myapp.Fonts_windows2000_en_us"</code>.
     * </p>
     * <p>
     * If no appropriate OS-specific resource bundle is found, the
     * process is repeated using the location as the base bundle name.
     * </p>
     * <p>
     * The property file contains entries that look like this:
     * <pre>
     *	textfont.0=MS Sans Serif-regular-10
     *	textfont.1=Times New Roman-regular-10
     *
     *	titlefont.0=MS Sans Serif-regular-12
     *	titlefont.1=Times New Roman-regular-12
     * </pre>
     * Each entry maps a symbolic font names (the font registry keys) with
     * a "<code>.<it>n</it></code> suffix to standard font names
     * on the right. The suffix indicated order of preference:
     * "<code>.0</code>" indicates the first choice,
     * "<code>.1</code>" indicates the second choice, and so on.
     * </p>
     * The following example shows how to use the font registry:
     * <pre>
     *	FontRegistry registry = new FontRegistry("com.example.myapp.fonts");
     *  Font font = registry.get("textfont");
     *  control.setFont(font);
     *  ...
     * </pre>
     *
     * @param location the name of the resource bundle
     * @param loader the ClassLoader to use to find the resource bundle
     * @exception MissingResourceException if the resource bundle cannot be found
     * @since 2.1
     */
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
