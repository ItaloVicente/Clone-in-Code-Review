    /**
     * Creates a ColorDescriptor from an existing color.
     *
     * The returned ColorDescriptor depends on the original Color. Disposing
     * the original colour while the color descriptor is still in use may cause
     * SWT to throw a graphic disposed exception.
     *
     * @since 3.1
     *
     * @param toCreate Color to generate a ColorDescriptor from
     * @return a newly created ColorDescriptor
     */
    public static ColorDescriptor createFrom(Color toCreate) {
        return new RGBColorDescriptor(toCreate);
    }
