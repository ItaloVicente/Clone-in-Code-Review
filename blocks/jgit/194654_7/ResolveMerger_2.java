  protected WorkingTreeIterator workingTreeIterator;

  protected MergeAlgorithm mergeAlgorithm;

  @NonNull
  private ContentMergeStrategy contentStrategy = ContentMergeStrategy.CONFLICT;

  private static MergeAlgorithm getMergeAlgorithm(Config config) {
    SupportedAlgorithm diffAlg = config.getEnum(
        CONFIG_DIFF_SECTION
        HISTOGRAM);
    return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
  }

  private static String[] defaultCommitNames() {
    return new String[]{"BASE"
  }

  private static final Attributes NO_ATTRIBUTES = new Attributes();

