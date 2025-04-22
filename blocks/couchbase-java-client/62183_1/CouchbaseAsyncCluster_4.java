    @Override
    public Observable<AsyncClusterManager> clusterManager() {
        String[][] creds = this.credentialsManager().getCredentials(AuthenticationContext.CLUSTER_MANAGEMENT, null);
        return Observable.just(
            (AsyncClusterManager) DefaultAsyncClusterManager.create(
                creds[0][0], creds[0][1], connectionString, environment, core
            )
        );
    }

