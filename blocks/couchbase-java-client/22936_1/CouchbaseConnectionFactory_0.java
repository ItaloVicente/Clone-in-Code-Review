  void checkConfigAgainstPersistence(PersistTo pers, ReplicateTo repl) {
    int nodeCount = getVBucketConfig().getServersCount();
    if(pers.getValue() > nodeCount) {
      throw new ObservedException("Currently, there are less nodes in the "
        + "cluster than required to satisfy the persistence constraint.");
    }
    if(repl.getValue() > nodeCount) {
      throw new ObservedException("Currently, there are less nodes in the "
        + "cluster than required to satisfy the replication constraint.");
    }
  }
