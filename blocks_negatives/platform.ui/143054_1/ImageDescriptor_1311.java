    /**
     * Creates and returns a new image descriptor from a URL.
     *
     * @param url The URL of the image file.
     * @return a new image descriptor
     */
    public static ImageDescriptor createFromURL(URL url) {
        if (url == null) {
            return getMissingImageDescriptor();
        }
        return new URLImageDescriptor(url);
    }
