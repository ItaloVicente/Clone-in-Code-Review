  private volatile short master;
  private final short replica1;
  private final short replica2;
  private final short replica3;

  public VBucket(final short m) {
    this(m, REPLICA_NOT_USED, REPLICA_NOT_USED, REPLICA_NOT_USED);
  }
