
  @Override
  public ReplicaGetOperation replicaGet(String key, ReplicaIndex index,
  ReplicaGetOperation.Callback callback) {
    throw new UnsupportedOperationException("Replica get is not supported "
        + "for ASCII protocol");
  }
