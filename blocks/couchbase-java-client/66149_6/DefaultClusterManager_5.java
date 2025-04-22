
    @Override
    @InterfaceStability.Experimental
    public ClusterApiClient apiClient() {
        return new ClusterApiClient(asyncClusterManager.username, asyncClusterManager.password, asyncClusterManager.core,
                asyncClusterManager.environment.viewTimeout(), TIMEOUT_UNIT);
    }
