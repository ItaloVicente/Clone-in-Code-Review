    public Observable<AsyncClusterManager> clusterManager(final String username,
        final String password) {
        return Observable.just(
            (AsyncClusterManager) DefaultAsyncClusterManager.create(
                username, password, connectionString, environment, core
            )
        );
