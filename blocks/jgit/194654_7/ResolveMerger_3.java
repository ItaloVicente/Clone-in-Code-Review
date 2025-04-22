  protected ResolveMerger(Repository local
    super(local);
    Config config = local.getConfig();
    mergeAlgorithm = getMergeAlgorithm(config);
    commitNames = defaultCommitNames();
    this.inCore = inCore;
  }

