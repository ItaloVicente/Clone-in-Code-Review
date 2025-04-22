    }

    /**
     * An image which can be shown in an "about" dialog for this
     * product. Products designed to run "headless" typically would not 
     * have such an image.
     * <p>
     * A full-sized product image (no larger than 500x330 pixels) is
     * shown without the "aboutText" blurb.  A half-sized product image
     * (no larger than 250x330 pixels) is shown with the "aboutText"
     * blurb beside it.
     */
    public static ImageDescriptor getAboutImage(IProduct product) {
        return getImage(product.getProperty(ABOUT_IMAGE), product
                .getDefiningBundle());
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
    public static ImageDescriptor[] getWindowImages(IProduct product) {
        String property = product.getProperty(WINDOW_IMAGES);

        if (property == null) {
