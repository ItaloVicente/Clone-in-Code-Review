    /**
     * Return an array of image descriptors for the window images to use for
     * this product. The expectations is that the elements will be the same
     * image rendered at different sizes. Products designed to run "headless"
     * typically would not have such images.
     *
     * @return an array of the image descriptors for the window images, or
     *         <code>null</code> if none
     */
    public ImageDescriptor[] getWindowImages() {
        if (windowImages == null && product != null) {
