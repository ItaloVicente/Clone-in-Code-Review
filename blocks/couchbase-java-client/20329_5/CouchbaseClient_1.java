  public OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo req, ReplicateTo rep)
    throws InterruptedException, ExecutionException {
    OperationFuture<Boolean> addOp = add(key, exp, value);

    if (addOp.get()) {
      addOp.set(false, addOp.getStatus());
      observePoll(key, addOp.getCas(), req, rep);
      addOp.set(true, addOp.getStatus());
    }
    return addOp;
  }
  public OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo req)
    throws InterruptedException, ExecutionException {
    return add(key, exp, value, req, ReplicateTo.ZERO);
  }

  public OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo req, ReplicateTo rep)
    throws InterruptedException, ExecutionException {
    OperationFuture<Boolean> replaceOp = replace(key, exp, value);
    if (replaceOp.get()) {
      replaceOp.set(false, replaceOp.getStatus());
      observePoll(key, replaceOp.getCas(), req, rep);
      replaceOp.set(true, replaceOp.getStatus());
    }
    return replaceOp;
  }
  public OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo req)
    throws InterruptedException, ExecutionException {
    return replace(key, exp, value, req, ReplicateTo.ZERO);
  }

  public CASResponse cas(String key, long cas,
          String value, PersistTo req, ReplicateTo rep)
    throws InterruptedException, ExecutionException {
    OperationFuture<CASResponse> casOp = asyncCAS(key, cas, value);
    CASResponse casr = null;
    casr = casOp.get();
    if (casr == CASResponse.OK) {
      observePoll(key, casOp.getCas(), req, rep);
    }
    return casr;
  }
  public CASResponse cas(String key, long casv,
          String value, PersistTo req)
    throws InterruptedException, ExecutionException {
    return cas(key, casv, value, req, ReplicateTo.ZERO);
  }

