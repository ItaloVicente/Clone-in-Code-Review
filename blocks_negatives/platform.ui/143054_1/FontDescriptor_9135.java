    /**
     * Creates a FontDescriptor that describes an existing font. The resulting
     * descriptor depends on the Font. Disposing the Font while the descriptor
     * is still in use may throw a graphic disposed exception.
     *
     * @since 3.1
     *
     * @deprecated use {@link FontDescriptor#createFrom(Font)}
     *
     * @param font a font to describe
     * @param originalDevice must be the same Device that was passed into
     * the font's constructor when it was first created.
     * @return a newly created FontDescriptor.
     */
    @Deprecated
