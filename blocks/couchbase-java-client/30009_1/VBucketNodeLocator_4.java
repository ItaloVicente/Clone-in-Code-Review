  public List<Integer> getReplicaIndexes(String key) {
    TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    int vbucket = config.getVbucketByKey(key);
    VBucket bucket = config.getVbuckets().get(vbucket);
    List<Integer> indexes = new ArrayList<Integer>();
    for (int i = 0; i < VBucket.MAX_REPLICAS; i++) {
      if (bucket.getReplica(i) != VBucket.REPLICA_NOT_USED) {
        indexes.add(i);
      }
    }
    return indexes;
  }

  public boolean hasActiveMaster(String key) {
    TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    int vbucket = config.getVbucketByKey(key);
    VBucket bucket = config.getVbuckets().get(vbucket);
    return bucket.getMaster() != -1;
  }

