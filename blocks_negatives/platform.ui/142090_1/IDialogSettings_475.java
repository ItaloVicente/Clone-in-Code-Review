    /**
     * Convenience API. Convert the value of the given key in this dialog
     * settings to a long and return it.
     *
     * @param key
     *            the key
     * @return the value coverted to long, or throws
     *         <code>NumberFormatException</code> if none
     *
     * @exception NumberFormatException
     *                if the string value does not contain a parsable number.
     * @see java.lang.Long#valueOf(java.lang.String)
     */
    long getLong(String key) throws NumberFormatException;
