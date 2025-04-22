
    /**
     * Shut down the monitor in a graceful way (and release all resources)
     */
    public void shutdown() {
        shutdown(-1, TimeUnit.MILLISECONDS);
