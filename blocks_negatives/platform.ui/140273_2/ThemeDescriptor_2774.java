        fonts.add(definition);
    }

    /**
     * Add a data object to this descriptor.
     *
     * @param key the key
     * @param data the data
     */
    void setData(String key, Object data) {
        if (dataMap.containsKey(key)) {
