    /**
     * Creates and returns a new image descriptor for the given image. Note
     * that disposing the original Image will cause the descriptor to become invalid.
     *
     * @since 3.1
     *
     * @param img image to create
     * @return a newly created image descriptor
     */
    public static ImageDescriptor createFromImage(Image img) {
        return new ImageDataImageDescriptor(img);
    }
