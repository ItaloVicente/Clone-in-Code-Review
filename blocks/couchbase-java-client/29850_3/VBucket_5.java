  public VBucket(final short m, final short r1) {
    this(m, r1, REPLICA_NOT_USED, REPLICA_NOT_USED);
  }

  public VBucket(final short m, final short r1, final short r2) {
    this(m, r1, r2, REPLICA_NOT_USED);
  }
