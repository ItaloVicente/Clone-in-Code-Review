        SeedNodesRequest request = new SeedNodesRequest(assembleSeedNodes(connectionString, environment));
        core.send(request).toBlocking().single();
        this.environment = environment;
        this.connectionString = connectionString;
    }

    private List<String> assembleSeedNodes(ConnectionString connectionString, CouchbaseEnvironment environment) {
