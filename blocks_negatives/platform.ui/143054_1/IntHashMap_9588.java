    /**
     * @param size
     * @param loadFactor
     */
    public IntHashMap(int size, float loadFactor) {
        map = new HashMap(size, loadFactor);
    }
