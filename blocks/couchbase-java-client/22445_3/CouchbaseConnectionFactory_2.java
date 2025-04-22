  public ClusterManager getClusterManager() {
    if(clusterManager == null) {
      clusterManager = new ClusterManager(storedBaseList, bucket, pass);
    }
    return clusterManager;
  }

