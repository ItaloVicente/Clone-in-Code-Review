        return create(username, password, connectionString.hosts(), environment, core);
    }

    public static DefaultClusterManager create(final String username, final String password,
                                               final List<ConnectionString.UnresolvedSocket> seedNodes, final CouchbaseEnvironment environment, final ClusterFacade core) {
        return new DefaultClusterManager(username, password, seedNodes, environment, core);
