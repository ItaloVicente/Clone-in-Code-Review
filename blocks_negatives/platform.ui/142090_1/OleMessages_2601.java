    /**
     * Returns the resource object with the given key in the resource bundle. If
     * there isn't any value under the given key, the key is returned.
     *
     * @param key
     *            the resource name
     * @return the string
     */
    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
