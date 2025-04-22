    /**
     * Create an url from the argument absolute or relative path. The bundle
     * parameter is used as the base for relative paths and is allowed to be
     * null.
     *
     * @param value
     *            the absolute or relative path
     * @param definingBundle
     *            bundle to be used for relative paths (may be null)
     * @return
     */
    public static URL getUrl(String value, Bundle definingBundle) {
        try {
            if (value != null) {
