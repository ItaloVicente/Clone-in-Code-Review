    /**
     * Returns a array of URL for the given property or <code>null</code>.
     * The property value should be a comma separated list of urls (either
     * absolute or relative to the argument bundle). Tokens that do not
     * represent a valid url will be represented with a null entry in the
     * returned array.
     * 
     * @param value
     *            value of a property that contains a comma-separated list of
     *            product relative urls
     * @param definingBundle
     *            bundle to be used as base for relative paths (may be null)
     * @return a URL for the given property, or <code>null</code>
     */
    public static URL[] getURLs(String value, Bundle definingBundle) {
        if (value == null) {
