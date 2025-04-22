  private void observePoll(String key, long cas,
          PersistTo persist, ReplicateTo replicate) {
    int persists, replicates;
    int totPersists, totReplicas;
    boolean masterPersisted;
    int loop = 0;
    long obsPollInt = cbConnFactory.getObsPollInterval();
