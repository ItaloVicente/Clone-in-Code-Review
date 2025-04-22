
  @Override
  public ReplicaGetsOperation replicaGets(String key, int index,
    ReplicaGetsOperation.Callback callback) {
    throw new UnsupportedOperationException("Replica gets is not supported "
      + "for ASCII protocol");
  }
