    /**
     * Create the decoration overlay for the base image using the array of
     * provided overlays. The indices of the array correspond to the values
     * of the 6 overlay constants defined on {@link IDecoration}
     * ({@link IDecoration#TOP_LEFT}, {@link IDecoration#TOP_RIGHT},
     * {@link IDecoration#BOTTOM_LEFT}, {@link IDecoration#BOTTOM_RIGHT},
     * {@link IDecoration#UNDERLAY}, and {@link IDecoration#REPLACE}).
     *
     * @param baseImage the base image
     * @param overlaysArray the overlay images, may contain null values
     * @param sizeValue the size of the resulting image
     */
    public DecorationOverlayIcon(Image baseImage,
            ImageDescriptor[] overlaysArray, Point sizeValue) {
