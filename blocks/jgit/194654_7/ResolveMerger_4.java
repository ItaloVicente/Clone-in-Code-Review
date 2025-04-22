  protected ResolveMerger(ObjectInserter inserter
    super(inserter);
    mergeAlgorithm = getMergeAlgorithm(config);
    commitNames = defaultCommitNames();
    inCore = true;
  }

  @NonNull
  public ContentMergeStrategy getContentMergeStrategy() {
    return contentStrategy;
  }

