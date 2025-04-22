     * Creates a new RGBColorDescriptor that describes an existing color.
     *
     * @since 3.1
     *
     * @param originalColor a color to describe
     */
    public RGBColorDescriptor(Color originalColor) {
        this(originalColor.getRGB());
        this.originalColor = originalColor;
    }
