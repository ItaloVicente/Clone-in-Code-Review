    /**
     * Returns a color descriptor for the given RGB values
     * @since 3.1
     *
     * @param toCreate RGB values to create
     * @return a new ColorDescriptor
     */
    public static ColorDescriptor createFrom(RGB toCreate) {
        return new RGBColorDescriptor(toCreate);
    }
