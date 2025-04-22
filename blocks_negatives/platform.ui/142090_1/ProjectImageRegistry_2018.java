    /**
     * Returns the image for the given nature id or
     * <code>null</code> if no image is registered for the given id
     */
    public ImageDescriptor getNatureImage(String natureId) {
        return map.get(natureId);
    }
