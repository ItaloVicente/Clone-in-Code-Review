    /**
     * @param key
     * @param defaultValue
     * @return the int value at the given key, or the default value if this map does not contain the given key
     */
    public int get(Object key, int defaultValue) {
        Integer result = (Integer)map.get(key);
