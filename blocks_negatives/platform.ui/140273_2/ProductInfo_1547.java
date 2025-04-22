    /**
     * Returns the application name or <code>null</code>. Note this is never
     * shown to the user.  It is used to initialize the SWT Display.
     * <p>
     * On Motif, for example, this can be used to set the name used
     * for resource lookup.
     * </p>
     *
     * @return the application name, or <code>null</code>
     *
     * @see org.eclipse.swt.widgets.Display#setAppName
     */
    public String getAppName() {
        if (appName == null && product != null) {
