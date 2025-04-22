    /**
     * Creates an ImageDescriptor based on the given original descriptor, but with additional
     * SWT flags.
     *
     * <p>
     * Note that this sort of ImageDescriptor is slower and consumes more resources than
     * a regular image descriptor. It will also never generate results that look as nice as
     * a hand-drawn image. Clients are encouraged to supply their own disabled/grayed/etc. images
     * rather than using a default image and transforming it.
     * </p>
     *
     * @param originalImage image to transform
     * @param swtFlags any flag that can be passed to the flags argument of Image#Image(Device, Image, int)
     * @return an ImageDescriptor that creates new images by transforming the given image descriptor
     *
     * @see Image#Image(Device, Image, int)
     * @since 3.1
     *
     */
    public static ImageDescriptor createWithFlags(ImageDescriptor originalImage, int swtFlags) {
        return new DerivedImageDescriptor(originalImage, swtFlags);
    }
