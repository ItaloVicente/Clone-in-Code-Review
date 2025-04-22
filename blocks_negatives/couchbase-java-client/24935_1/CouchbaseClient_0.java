    int vBucketIndex = locator.getVBucketIndex(key);
    int currentReplicaNum = cfg.getReplica(vBucketIndex, numReplica-1);

    if (currentReplicaNum < 0) {
      throw new ObservedException("Currently, there is no replica available for"
        + "the given replica index. This can be the case because of a failed "
        + "over node which has not yet been rebalanced.");
