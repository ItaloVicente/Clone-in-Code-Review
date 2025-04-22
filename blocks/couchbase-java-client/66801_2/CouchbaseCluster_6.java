
    @Override
    public CouchbaseCluster authenticate(Authenticator auth) {
        couchbaseAsyncCluster.authenticate(auth);
        return this;
    }

    @Override
    public Authenticator authenticator() {
        return couchbaseAsyncCluster.authenticator();
    }
