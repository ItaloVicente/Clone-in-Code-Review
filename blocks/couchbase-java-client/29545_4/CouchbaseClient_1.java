    int donePolls = 0;
    int alreadyPersistedTo = 0;
    int alreadyReplicatedTo = 0;
    boolean alreadyPersistedToMaster = false;
    while(shouldReplicateTo > alreadyReplicatedTo
      || shouldPersistTo - 1 > alreadyPersistedTo
      || (!alreadyPersistedToMaster && shouldPersistToMaster)) {
      checkObserveReplica(key, shouldPersistTo, shouldReplicateTo);
