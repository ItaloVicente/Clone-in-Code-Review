    }

    /**
     * Returns the text to show in an "about" dialog for this product.
     * Products designed to run "headless" typically would not have such text.
     *
     * @return the about text, or <code>null</code> if none
     */
    public String getAboutText() {
        return productProperties == null ? null : productProperties
                .getAboutText();
    }

    /**
     * Returns the application name or <code>null</code>.
     * Note this is never shown to the user.
     * It is used to initialize the SWT Display.
     * <p>
     * On Motif, for example, this can be used
     * to set the name used for resource lookup.
     * </p>
     *
     * @return the application name, or <code>null</code>
     *
     * @see org.eclipse.swt.widgets.Display#setAppName
     */
    public String getAppName() {
        return productProperties == null ? null : productProperties
                .getAppName();
    }

    /**
     * Returns the product name or <code>null</code>.
     * This is shown in the window title and the About action.
     *
     * @return the product name, or <code>null</code>
     */
    public String getProductName() {
        return productProperties == null ? null : productProperties
                .getProductName();
    }

    /**
     * Returns the provider name or <code>null</code>.
     *
     * @return the provider name, or <code>null</code>
     */
    public String getProviderName() {
        return bundleGroupProperties == null ? null : bundleGroupProperties
                .getProviderName();
    }

    /**
     * Returns the feature version id.
     *
     * @return the version id of the feature
     */
    public String getVersionId() {
    }

    /**
     * Returns a <code>URL</code> for the welcome page.
     * Products designed to run "headless" typically would not have such an page.
     *
     * @return the welcome page, or <code>null</code> if none
     */
    public URL getWelcomePageURL() {
        if (productProperties != null) {
