  private void checkObserveReplica(int numPersist, int numReplica) {
    Config cfg = ((CouchbaseConnectionFactory) connFactory).getVBucketConfig();
    VBucketNodeLocator locator = ((VBucketNodeLocator)
        ((CouchbaseConnection) mconn).getLocator());

    int replicaCount = Math.min(locator.getAll().size() - 1,
          cfg.getReplicasCount());

    if (numReplica > replicaCount) {
      throw new ObservedException("Requested replication to " + numReplica
          + " node(s), but only " + replicaCount + " are avaliable");
    } else if (numPersist > replicaCount + 1) {
      throw new ObservedException("Requested persistence to " + numPersist
          + " node(s), but only " + (replicaCount + 1) + " are available.");
    }
  }

