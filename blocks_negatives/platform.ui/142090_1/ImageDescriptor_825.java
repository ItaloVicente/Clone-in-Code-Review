    /**
     * Creates and returns a new image descriptor for the given image. This
     * method takes the Device that created the Image as an argument, allowing
     * the original Image to be reused if the descriptor is asked for another
     * Image on the same device. Note that disposing the original Image will
     * cause the descriptor to become invalid.
     *
     * @deprecated use {@link ImageDescriptor#createFromImage(Image)}
     * @since 3.1
     *
     * @param img image to create
     * @param theDevice the device that was used to create the Image
     * @return a newly created image descriptor
     */
    @Deprecated
