    int persistReplica = persist.getValue() > 0 ? persist.getValue() - 1 : 0;
    int replicateTo = replicate.getValue();
    int obsPolls = 0;
    int obsPollMax = cbConnFactory.getObsPollMax();
    long obsPollInterval = cbConnFactory.getObsPollInterval();
    boolean persistMaster = persist.getValue() > 0;
