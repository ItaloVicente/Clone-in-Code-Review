  public ClusterManager getClusterManager() {
    if(clusterManager == null) {
      clusterManager = new ClusterManager(getBaseList(), getBucketName(),
        getPassword());
    }
    return clusterManager;
  }

