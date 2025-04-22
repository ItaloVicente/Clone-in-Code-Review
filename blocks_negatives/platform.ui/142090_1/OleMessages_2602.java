    /**
     * Returns the resource object with the given key in the resource bundle. If
     * there isn't any value under the given key, the default value is returned.
     *
     * @param key
     *            the resource name
     * @param def
     *            the default value
     * @return the string
     */
    public static String getString(String key, String def) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return def;
        }
    }
