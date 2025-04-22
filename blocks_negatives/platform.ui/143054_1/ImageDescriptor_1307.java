    /**
     * Creates and returns a new image descriptor given an ImageDataProvider
     * describing the image.
     *
     * @param provider contents of the image
     * @return newly created image descriptor
     * @since 3.13
     */
    public static ImageDescriptor createFromImageDataProvider(ImageDataProvider provider) {
    	return new ImageDataImageDescriptor(provider);
    }
