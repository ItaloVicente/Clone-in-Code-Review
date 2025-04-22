    public Boolean disconnect(long timeout, TimeUnit timeUnit) {
        return couchbaseAsyncCluster
            .disconnect()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
