    /**
     * Creates a new size cache for the given set of controls, discarding any
     * existing cache.
     *
     * @param controls the controls whose size is being cached
     */
    private void rebuildCache(Control[] controls) {
        SizeCache[] newCache = new SizeCache[controls.length];
