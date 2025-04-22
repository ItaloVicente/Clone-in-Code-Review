    @Override
    public CouchbaseAsyncCluster authenticate(Authenticator auth) {
        if (auth == null) {
            LOGGER.trace("Authenticator was set to null, ignored");
            return this;
        }
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

