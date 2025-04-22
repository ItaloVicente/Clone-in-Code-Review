    int persistReplica = persist.getValue() > 0 ? persist.getValue() - 1 : 0;
    int replicateTo = replicate.getValue();
    int obsPolls = 0;
    int obsPollMax = cbConnFactory.getObsPollMax();
    long obsPollInterval = cbConnFactory.getObsPollInterval();
    boolean persistMaster = persist.getValue() > 0;

    VBucketNodeLocator locator = ((VBucketNodeLocator)
        ((CouchbaseConnection) mconn).getLocator());

    checkObserveReplica(key, persistReplica, replicateTo);

    int replicaPersistedTo = 0;
    int replicatedTo = 0;
    boolean persistedMaster = false;
    while(replicateTo > replicatedTo || persistReplica - 1 > replicaPersistedTo
        || (!persistedMaster && persistMaster)) {
      checkObserveReplica(key, persistReplica, replicateTo);

      if (++obsPolls >= obsPollMax) {
        long timeTried = obsPollMax * obsPollInterval;
        TimeUnit tu = TimeUnit.MILLISECONDS;
