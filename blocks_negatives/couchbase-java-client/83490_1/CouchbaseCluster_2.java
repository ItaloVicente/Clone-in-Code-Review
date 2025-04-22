            .clusterManager(username, password)
            .map(new Func1<AsyncClusterManager, ClusterManager>() {
                @Override
                public ClusterManager call(AsyncClusterManager asyncClusterManager) {
                    return DefaultClusterManager.create(username, password, connectionString,
                        environment, core());
                }
            })
            .toBlocking()
            .single();
