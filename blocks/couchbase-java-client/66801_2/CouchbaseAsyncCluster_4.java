    @Override
    public CouchbaseAsyncCluster authenticate(Authenticator auth) {
        this.authenticator = auth;
        if (!bucketCache.isEmpty()) {
            LOGGER.warn("Authenticator was switched while {} buckets are still open. Operations on these buckets" +
                    " will continue using the old Authenticator until you close and reopen them");
        }
        return this;
    }

    @Override
    public Authenticator authenticator() {
        return this.authenticator;
    }

