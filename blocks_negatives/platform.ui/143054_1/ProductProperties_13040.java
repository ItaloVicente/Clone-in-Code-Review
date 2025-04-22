        return productId;
    }

    /**
     * The application name, used to initialize the SWT Display.  This
     * value is distinct from the string displayed in the application
     * title bar.
     * <p>
     * E.g., On motif, this can be used to set the name used for
     * resource lookup.
     * </p>
     * <p>
     * The returned value will have {n} values substituted based on the
     * current product's mappings regardless of the given product argument.
     * </p>
     * @see org.eclipse.swt.widgets.Display#setAppName
     */
    public static String getAppName(IProduct product) {
        String property = product.getProperty(APP_NAME);
        if (property == null) {
