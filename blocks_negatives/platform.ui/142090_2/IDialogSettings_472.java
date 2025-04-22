    /**
     * Convenience API. Convert the value of the given key in this dialog
     * settings to a double and return it.
     *
     * @param key
     *            the key
     * @return the value coverted to double, or throws
     *         <code>NumberFormatException</code> if none
     *
     * @exception NumberFormatException
     *                if the string value does not contain a parsable number.
     * @see java.lang.Double#valueOf(java.lang.String)
     */
    double getDouble(String key) throws NumberFormatException;
