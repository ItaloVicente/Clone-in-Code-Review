    /**
     * Returns the resource object with the given key in
     * the resource bundle. If there isn't any value under
     * the given key, the key is returned, surrounded by '!'s.
     *
     * @param key the resource name
     * @return the string
     */
    public static String getString(String key) {
        try {
            return fgResourceBundle.getString(key);
        } catch (MissingResourceException e) {
        }
    }
