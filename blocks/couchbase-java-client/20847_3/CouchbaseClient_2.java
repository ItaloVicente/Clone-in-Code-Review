  public void observePoll(String key, long cas, PersistTo persist,
      ReplicateTo replicate, boolean isDelete) {
    boolean persistMaster = false;
    int persistReplica = persist.getValue();
    int replicateTo = replicate.getValue();
    int obsPolls = 0;
