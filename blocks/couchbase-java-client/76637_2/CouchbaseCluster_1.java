    @Override
    public CouchbaseCluster authenticate(String username, String password) {
        couchbaseAsyncCluster.authenticate(username, password);
        return this;
    }

