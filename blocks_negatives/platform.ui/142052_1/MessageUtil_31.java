    /**
     * Returns the formatted message for the given key in
     * the resource bundle.
     *
     * @param key the resource name
     * @param args the message arguments
     * @return the string
     */
    public static String format(String key, Object[] args) {
        return MessageFormat.format(getString(key), args);
    }
