
  @Override
  public ReplicaGetOperation replicaGet(String key, int index,
  ReplicaGetOperation.Callback callback) {
    throw new UnsupportedOperationException("Replica get is not supported "
        + "for ASCII protocol");
  }
