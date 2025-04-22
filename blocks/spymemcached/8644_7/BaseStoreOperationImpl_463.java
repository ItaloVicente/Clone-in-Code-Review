  public BaseStoreOperationImpl(String t, String k, int f, int e, byte[] d,
      OperationCallback cb) {
    super(cb);
    type = t;
    key = k;
    flags = f;
    exp = e;
    data = d;
  }
