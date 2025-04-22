    /**
     * Creates and returns a new SWT <code>ImageData</code> object
     * for this image descriptor.
     * Note that each call returns a new SWT image data object.
     * <p>
     * This framework method is declared public so that it is
     * possible to request an image descriptor's image data without
     * creating an SWT image object.
     * </p>
     * <p>
     * Returns <code>null</code> if the image data could not be created.
     * </p>
     * <p>
     * This method was abstract until 3.13. Clients should stop re-implementing
     * this method and should re-implement {@link #getImageData(int)} instead.
     * </p>
     *
     * @return a new image data or <code>null</code>
