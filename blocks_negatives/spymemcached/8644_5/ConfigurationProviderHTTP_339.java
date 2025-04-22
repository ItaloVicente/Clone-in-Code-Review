    /**
     * Unsubscribe from updates on a given bucket and given reconfigurable
     * @param vbucketName bucket name
     * @param rec reconfigurable
     */
    public void unsubscribe(String vbucketName, Reconfigurable rec) {
        BucketMonitor monitor = this.monitors.get(vbucketName);
        if (monitor != null) {
            monitor.deleteObserver(new ReconfigurableObserver(rec));
