        }

        return null;
    }

    /**
     * Returns the descriptor for an image which can be shown in an "about" dialog
     * for this product. Products designed to run "headless" typically would not
     * have such an image.
     *
     * @return the descriptor for an about image, or <code>null</code> if none
     */
    public ImageDescriptor getAboutImage() {
        return productProperties == null ? null : productProperties
                .getAboutImage();
    }

    /**
     * Returns the descriptor for an image which can be shown in an "about features"
     * dialog. Products designed to run "headless" typically would not have such an image.
     *
     * @return the descriptor for a feature image, or <code>null</code> if none
     */
    public ImageDescriptor getFeatureImage() {
        return bundleGroupProperties == null ? null : bundleGroupProperties
                .getFeatureImage();
    }

    /**
     * Returns the simple name of the feature image file.
     *
     * @return the simple name of the feature image file,
     * or <code>null</code> if none
     */
    public String getFeatureImageName() {
        if (bundleGroupProperties == null) {
