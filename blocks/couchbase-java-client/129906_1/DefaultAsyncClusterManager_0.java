        return create(username, password, connectionString.hosts(), environment, core);
    }

    public static DefaultAsyncClusterManager create(final String username, final String password,
                                                    final List<ConnectionString.UnresolvedSocket> seedNodes, final CouchbaseEnvironment environment, final ClusterFacade core) {
        return new DefaultAsyncClusterManager(username, password, seedNodes, environment, core);
