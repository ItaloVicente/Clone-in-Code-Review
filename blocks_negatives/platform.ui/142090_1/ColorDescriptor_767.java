    /**
     * Creates a ColorDescriptor from an existing Color, given the Device associated
     * with the original Color. This is the usual way to convert a Color into
     * a ColorDescriptor. Note that the returned ColorDescriptor depends on the
     * original Color, and disposing the Color will invalidate the ColorDescriptor.
     *
     * @deprecated use {@link ColorDescriptor#createFrom(Color)}
     *
     * @since 3.1
     *
     * @param toCreate Color to convert into a ColorDescriptor.
     * @param originalDevice this must be the same Device that was passed into the
     * original Color's constructor.
     * @return a newly created ColorDescriptor that describes the given Color.
     */
    @Deprecated
