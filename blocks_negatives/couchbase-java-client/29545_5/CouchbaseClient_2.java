    int replicaPersistedTo = 0;
    int replicatedTo = 0;
    boolean persistedMaster = false;
    while(replicateTo > replicatedTo || persistReplica - 1 > replicaPersistedTo
        || (!persistedMaster && persistMaster)) {
      checkObserveReplica(key, persistReplica, replicateTo);
