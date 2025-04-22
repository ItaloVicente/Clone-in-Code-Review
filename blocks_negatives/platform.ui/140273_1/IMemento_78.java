    /**
     * Returns the id for this memento.
     *
     * @return the memento id, or <code>null</code> if none
     * @see #createChild(java.lang.String,java.lang.String)
     */
    String getID();

    /**
     * Returns the integer value of the given key.
     *
     * @param key the key
     * @return the value, or <code>null</code> if the key was not found or was found
     *   but was not an integer
     */
    Integer getInteger(String key);

    /**
     * Returns the string value of the given key.
     *
     * @param key the key
     * @return the value, or <code>null</code> if the key was not found
     */
    String getString(String key);

    /**
