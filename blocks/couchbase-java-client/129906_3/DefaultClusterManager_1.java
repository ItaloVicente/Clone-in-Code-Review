        this.asyncClusterManager = asyncClusterManager;
        this.timeout = environment.managementTimeout();
        this.username = username;
        this.password = password;
        this.core = core;
    }

    DefaultClusterManager(final String username, final String password, final List<ConnectionString.UnresolvedSocket> seedNodes,
                          final CouchbaseEnvironment environment, final ClusterFacade core) {
        asyncClusterManager = DefaultAsyncClusterManager.create(username, password, seedNodes, environment,
