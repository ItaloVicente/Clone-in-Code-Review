    /**
     * Sets the value of the given key to the given floating point number.
     *
     * @param key the key
     * @param value the value
     */
    void putFloat(String key, float value);

    /**
     * Sets the value of the given key to the given integer.
     *
     * @param key the key
     * @param value the value
     */
    void putInteger(String key, int value);

    /**
     * Copy the attributes and children from  <code>memento</code>
     * to the receiver.
     *
     * @param memento the IMemento to be copied.
     */
    void putMemento(IMemento memento);

    /**
     * Sets the value of the given key to the given string.
     *
     * @param key the key
     * @param value the value
     */
    void putString(String key, String value);

    /**
