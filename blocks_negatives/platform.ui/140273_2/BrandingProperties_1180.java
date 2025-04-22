    /**
     * Create a descriptor from the argument absolute or relative path to an
     * image file. bundle parameter is used as the base for relative paths and
     * is allowed to be null.
     *
     * @param value
     *            the absolute or relative path
     * @param definingBundle
     *            bundle to be used for relative paths (may be null)
     * @return
     */
    public static ImageDescriptor getImage(String value, Bundle definingBundle) {
        URL url = getUrl(value, definingBundle);
        return url == null ? null : ImageDescriptor.createFromURL(url);
    }
