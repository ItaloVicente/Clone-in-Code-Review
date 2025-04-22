    public static CouchbaseCluster create() {
        return create(DEFAULT_HOST);
    }

    public static CouchbaseCluster create(final String... nodes) {
        return create(Arrays.asList(nodes));
    }

    public static CouchbaseCluster create(final List<String> nodes) {
        return create(DefaultClusterEnvironment.create(), nodes);
    }

    public static CouchbaseCluster create(final ClusterEnvironment environment, final String... nodes) {
        return create(environment, Arrays.asList(nodes));
    }

    public static CouchbaseCluster create(final ClusterEnvironment environment, final List<String> nodes) {
        ConnectionString connectionString;
        if (nodes.size() == 1 && nodes.get(0).contains("://")) {
            connectionString = ConnectionString.create(nodes.get(0));
        } else {
            connectionString = ConnectionString.fromHostnames(nodes);
        }
        return from(environment, connectionString);
    }

    public static CouchbaseCluster from(final ConnectionString connectionString) {
        return from(DefaultClusterEnvironment.create(), connectionString);
    }

    public static CouchbaseCluster from(final ClusterEnvironment environment, final ConnectionString connectionString) {
        return new CouchbaseCluster(environment, connectionString);
    }

    CouchbaseCluster(final ClusterEnvironment environment, final ConnectionString connectionString) {
        core = new CouchbaseCore(environment);
        List<String> seedNodes = new ArrayList<String>();
        for (InetSocketAddress node : connectionString.hosts()) {
            seedNodes.add(node.getHostName());
        }
        if (seedNodes.isEmpty()) {
            seedNodes.add(DEFAULT_HOST);
        }
        SeedNodesRequest request = new SeedNodesRequest(seedNodes);
