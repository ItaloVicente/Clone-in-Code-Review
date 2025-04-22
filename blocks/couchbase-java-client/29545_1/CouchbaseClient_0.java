    final int maxPolls = cbConnFactory.getObsPollMax();
    final long pollInterval = cbConnFactory.getObsPollInterval();
    final VBucketNodeLocator locator = (VBucketNodeLocator) mconn.getLocator();

    final int shouldPersistTo = persist.getValue() > 0 ? persist.getValue() - 1 : 0;
    final int shouldReplicateTo = replicate.getValue();
    final boolean shouldPersistToMaster = persist.getValue() > 0;

    int donePolls = 0;
    int alreadyPersistedTo = 0;
    int alreadyReplicatedTo = 0;
    boolean alreadyPersistedToMaster = false;
    while(shouldReplicateTo > alreadyReplicatedTo
      || shouldPersistTo - 1 > alreadyPersistedTo
      || (!alreadyPersistedToMaster && shouldPersistToMaster)) {
      checkObserveReplica(key, shouldPersistTo, shouldReplicateTo);

      if (++donePolls >= maxPolls) {
        long timeTried = maxPolls * pollInterval;
