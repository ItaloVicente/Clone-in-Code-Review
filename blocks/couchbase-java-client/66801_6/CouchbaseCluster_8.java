
    @Override
    public CouchbaseCluster authenticate(Authenticator auth) {
        couchbaseAsyncCluster.authenticate(auth);
        return this;
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public Authenticator authenticator() {
        return couchbaseAsyncCluster.authenticator();
    }
