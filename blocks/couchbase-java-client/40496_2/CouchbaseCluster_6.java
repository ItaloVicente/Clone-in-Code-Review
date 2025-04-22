
    @Override
    public Observable<ClusterManager> clusterManager(final String username, final String password) {
        return Observable.just((ClusterManager) CouchbaseClusterManager.create(username, password, connectionString,
            environment, core));
    }
