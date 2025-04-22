    @Override
    public ClusterManager clusterManager() {
        final Credential cred = couchbaseAsyncCluster.getSingleCredential(CredentialContext.CLUSTER_MANAGEMENT, null);
        return couchbaseAsyncCluster
                .clusterManager(cred.login(), cred.password())
                .map(new Func1<AsyncClusterManager, ClusterManager>() {
                    @Override
                    public ClusterManager call(AsyncClusterManager asyncClusterManager) {
                        return DefaultClusterManager.create(cred.login(), cred.password(), connectionString,
                                environment, core());
                    }
                })
                .toBlocking()
                .single();
    }

