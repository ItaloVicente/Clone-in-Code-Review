    /**
     * Returns the descriptor for an image which can be shown in an "about" dialog
     * for this product. Products designed to run "headless" typically would not
     * have such an image.
     *
     * @return the descriptor for an about image, or <code>null</code> if none
     */
    public ImageDescriptor getAboutImage() {
        if (aboutImage == null && product != null) {
