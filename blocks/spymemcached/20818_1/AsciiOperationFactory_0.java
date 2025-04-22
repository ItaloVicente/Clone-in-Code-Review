  public ReplicaReadOperation replica(String key,
      ReplicaReadOperation.Callback cb) {
    throw new UnsupportedOperationException("Replica Read is not supported "
        + "for ASCII protocol");
  }

