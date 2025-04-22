     * Return an array of all bundles contained in this workbench.
     *
     * @return an array of bundles in the workbench or an empty array if none
     * @since 3.0
     */
    public Bundle[] getBundles() {
        return bundleContext == null ? new Bundle[0] : bundleContext
                .getBundles();
    }

    /**
     * Returns the bundle context associated with the workbench plug-in.
     *
     * @return the bundle context
     * @since 3.1
     */
    public BundleContext getBundleContext() {
    	return bundleContext;
    }

    /**
     * Returns the application name.
     * <p>
     * Note this is never shown to the user.
     * It is used to initialize the SWT Display.
     * On Motif, for example, this can be used
     * to set the name used for resource lookup.
     * </p>
     *
     * @return the application name, or <code>null</code>
     * @see org.eclipse.swt.widgets.Display#setAppName
     * @since 3.0
     */
    public String getAppName() {
        return getProductInfo().getAppName();
    }

    /**
     * Returns the name of the product.
     *
     * @return the product name, or <code>null</code> if none
     * @since 3.0
     */
    public String getProductName() {
        return getProductInfo().getProductName();
    }

    /**
     * Returns the image descriptors for the window image to use for this product.
     *
     * @return an array of the image descriptors for the window image, or
     *         <code>null</code> if none
     * @since 3.0
     */
    public ImageDescriptor[] getWindowImages() {
        return getProductInfo().getWindowImages();
    }

    /**
     * Returns an instance that describes this plugin's product (formerly "primary
     * plugin").
     * @return ProductInfo the product info for the receiver
     */
    private ProductInfo getProductInfo() {
        if (productInfo == null) {
