
    @Override
    @InterfaceStability.Experimental
    public ClusterApiClient apiClient() {
        return new ClusterApiClient(asyncClusterManager.username, asyncClusterManager.password, asyncClusterManager.core,
                this.timeout, TIMEOUT_UNIT); //uses the management timeout as default for API calls as well
    }
