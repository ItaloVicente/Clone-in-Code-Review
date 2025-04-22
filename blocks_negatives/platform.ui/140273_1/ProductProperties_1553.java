        this.product = product;
    }

    /**
     * The application name, used to initialize the SWT Display.  This
     * value is distinct from the string displayed in the application
     * title bar.
     * <p>
     * E.g., On motif, this can be used to set the name used for
     * resource lookup.
     * </p>
     * @see org.eclipse.swt.widgets.Display#setAppName
     */
    public String getAppName() {
        if (appName == null) {
