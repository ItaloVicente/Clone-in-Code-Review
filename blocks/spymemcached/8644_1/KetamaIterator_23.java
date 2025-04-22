  private final String key;
  private long hashVal;
  private int remainingTries;
  private int numTries = 0;
  private final HashAlgorithm hashAlg;
  private final TreeMap<Long, MemcachedNode> ketamaNodes;
