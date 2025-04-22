    int replicaPersistedTo = 0;
    int replicatedTo = 0;
    boolean persistedMaster = false;
    while(replicateTo > replicatedTo || persistReplica - 1 > replicaPersistedTo
        || (!persistedMaster && persistMaster)) {
      if (++obsPolls >= obsPollMax) {
        long timeTried = obsPollMax * obsPollInterval;
        TimeUnit tu = TimeUnit.MILLISECONDS;
        throw new ObservedTimeoutException("Observe Timeout - Polled"
            + " Unsuccessfully for at least " + tu.toSeconds(timeTried)
            + " seconds.");
