    /**
     * Convenience API. Convert the value of the given key in this dialog
     * settings to a int and return it.
     *
     * @param key
     *            the key
     * @return the value coverted to int, or throws
     *         <code>NumberFormatException</code> if none
     *
     * @exception NumberFormatException
     *                if the string value does not contain a parsable number.
     * @see java.lang.Integer#valueOf(java.lang.String)
     */
    int getInt(String key) throws NumberFormatException;
