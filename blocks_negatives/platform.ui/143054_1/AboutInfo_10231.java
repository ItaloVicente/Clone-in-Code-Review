        return null;
    }

    /**
     * Returns the ID of a perspective in which to show the welcome page.
     * May be <code>null</code>.
     *
     * @return the welcome page perspective id, or <code>null</code> if none
     */
    public String getWelcomePerspectiveId() {
        return bundleGroupProperties == null ? null : bundleGroupProperties
                .getWelcomePerspective();
    }

    /**
     * Returns a <code>String</code> for the tips and trick href.
     *
     * @return the tips and tricks href, or <code>null</code> if none
     */
    public String getTipsAndTricksHref() {
        return bundleGroupProperties == null ? null : bundleGroupProperties
                .getTipsAndTricksHref();
    }

    /**
     * Return an array of image descriptors for the window images to use for
     * this product. The expectations is that the elements will be the same
     * image rendered at different sizes. Products designed to run "headless"
     * typically would not have such images.
     *
     * @return an array of the image descriptors for the window images, or
     *         <code>null</code> if none
     * @since 3.0
     */
    public ImageDescriptor[] getWindowImages() {
        return productProperties == null ? null : productProperties
                .getWindowImages();
    }

    public String getBrandingBundleId() {
    	return bundleGroupProperties == null ? null : bundleGroupProperties
                .getBrandingBundleId();
    }

    public String getBrandingBundleVersion() {
    	return bundleGroupProperties == null ? null : bundleGroupProperties
                .getBrandingBundleVersion();
    }
