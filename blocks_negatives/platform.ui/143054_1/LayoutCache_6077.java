    /**
     * Flushes the cache for the given control. This should be called if exactly
     * one of the controls has changed but the remaining controls remain unmodified
     *
     * @param controlIndex
     */
    public void flush(int controlIndex) {
        caches[controlIndex].flush();
    }
