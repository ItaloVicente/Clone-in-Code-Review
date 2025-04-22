    /**
     * Flushes the cache.
     */
    public void flush() {
        for (SizeCache cache : caches) {
            cache.flush();
        }
    }
