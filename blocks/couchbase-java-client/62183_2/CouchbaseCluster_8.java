                .clusterManager(username, password)
                .map(new Func1<AsyncClusterManager, ClusterManager>() {
                    @Override
                    public ClusterManager call(AsyncClusterManager asyncClusterManager) {
                        return DefaultClusterManager.create(asyncClusterManager, environment);
                    }
                })
                .toBlocking()
                .single();
    }

    @Override
    public ClusterManager clusterManager() {
        return couchbaseAsyncCluster
            .clusterManager()
