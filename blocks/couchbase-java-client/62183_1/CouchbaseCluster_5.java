        credentialsManager().addClusterCredentials(username, password);
        return clusterManager();
    }

    @Override
    public ClusterManager clusterManager() {
