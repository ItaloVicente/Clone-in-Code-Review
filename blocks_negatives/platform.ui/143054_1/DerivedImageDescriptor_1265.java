    /**
     * Create a new image descriptor
     * @param original the original one
     * @param swtFlags flags to be used when image is created {@link Image#Image(Device, Image, int)}
     * @see SWT#IMAGE_COPY
     * @see SWT#IMAGE_DISABLE
     * @see SWT#IMAGE_GRAY
     */
    public DerivedImageDescriptor(ImageDescriptor original, int swtFlags) {
        this.original = original;
        flags = swtFlags;
    }
