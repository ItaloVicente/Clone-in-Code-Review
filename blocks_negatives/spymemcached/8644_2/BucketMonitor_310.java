
    /**
     * Shut down this monitor in a graceful way
     *
     * @param timeout
     * @param unit
     */
    public void shutdown(long timeout, TimeUnit unit) {
        deleteObservers();
        if (channel != null) {
            channel.close().awaitUninterruptibly(timeout, unit);
        }
        factory.releaseExternalResources();
