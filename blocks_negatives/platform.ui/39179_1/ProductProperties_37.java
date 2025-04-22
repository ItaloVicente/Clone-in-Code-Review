        return aboutText;
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
    public ImageDescriptor getAboutImage() {
        if (aboutImageDescriptor == null) {
