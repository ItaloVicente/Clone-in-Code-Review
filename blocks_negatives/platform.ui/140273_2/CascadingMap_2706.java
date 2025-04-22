    /**
     * Get the value.  Preference will be given to entries in the override map.
     *
     * @param key the key
     * @return the value
     */
    public Object get(Object key) {
        if (override.containsKey(key)) {
