    /**
     * Shutdowns a monitor connections to the REST service
     */
    public void shutdown() {
        for (BucketMonitor monitor : this.monitors.values()) {
            monitor.shutdown();
        }
