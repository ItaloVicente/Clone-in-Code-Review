    @Override
    public CouchbaseAsyncCluster authenticate(Authenticator auth) {
        this.authenticator = auth;
        if (!bucketCache.isEmpty()) {
            LOGGER.warn("Authenticator was switched while {} buckets are still open. Operations on these buckets" +
                    " will continue using the old Authenticator until you close and reopen them", bucketCache.size());
        }
        return this;
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public Authenticator authenticator() {
        return authenticator;
    }

