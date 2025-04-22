    @Override
    public ClusterManager clusterManager() {
        final Credential cred = couchbaseAsyncCluster.getCredential(CredentialContext.CLUSTER_MANAGEMENT, null);
        return couchbaseAsyncCluster
                .clusterManager(cred.getLogin(), cred.getPassword())
                .map(new Func1<AsyncClusterManager, ClusterManager>() {
                    @Override
                    public ClusterManager call(AsyncClusterManager asyncClusterManager) {
                        return DefaultClusterManager.create(cred.getLogin(), cred.getPassword(), connectionString,
                                environment, core());
                    }
                })
                .toBlocking()
                .single();
    }

