        return aboutImageDescriptor;
    }

    /**
     * An array of one or more images to be used for this product.  The
     * expectation is that the array will contain the same image rendered
     * at different sizes (16x16 and 32x32).  
     * Products designed to run "headless" typically would not have such images.
     * <p>
     * If this property is given, then it supercedes <code>WINDOW_IMAGE</code>.
     * </p>
     */
    public ImageDescriptor[] getWindowImages() {
        if (windowImageDescriptors == null) {
