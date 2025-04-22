    /**
     * Original image being described, or null if this image is described
     * completely using its ImageData
     */
    private Image originalImage = null;

    /**
     * Creates an image descriptor, given an image and the device it was created on.
     *
     * @param originalImage
     */
    ImageDataImageDescriptor(Image originalImage) {
