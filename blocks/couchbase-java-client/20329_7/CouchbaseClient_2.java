  public OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo req, ReplicateTo rep) {

    OperationFuture<Boolean> addOp = add(key, exp, value);

    boolean addStatus = false;

    try {
      addStatus = addOp.get();
    } catch (InterruptedException e) {
      addOp.set(false, new OperationStatus(false, "Add get timed out"));
    } catch (ExecutionException e) {
      addOp.set(false, new OperationStatus(false, "Add get "
              + "execution exception "));
    }
    if (!addStatus) {
      return addOp;
    }
    try {
      observePoll(key, 0x0L, req, rep);
      addOp.set(true, addOp.getStatus());
    } catch (ObservedException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      addOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return addOp;
  }

  public OperationFuture<Boolean> add(String key, int exp,
          String value, PersistTo req) {
    return add(key, exp, value, req, ReplicateTo.ZERO);
  }

  public OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo req, ReplicateTo rep) {

    OperationFuture<Boolean> replaceOp = replace(key, exp, value);

    boolean replaceStatus = false;

    try {
      replaceStatus = replaceOp.get();
    } catch (InterruptedException e) {
      replaceOp.set(false, new OperationStatus(false, "Replace get timed out"));
    } catch (ExecutionException e) {
      replaceOp.set(false, new OperationStatus(false, "Replace get "
              + "execution exception "));
    }
    if (!replaceStatus) {
      return replaceOp;
    }
    try {
      observePoll(key, 0x0L, req, rep);
      replaceOp.set(true, replaceOp.getStatus());
    } catch (ObservedException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return replaceOp;

  }
  public OperationFuture<Boolean> replace(String key, int exp,
          String value, PersistTo req) {
    return replace(key, exp, value, req, ReplicateTo.ZERO);
  }

  public CASResponse cas(String key, long cas,
          String value, PersistTo req, ReplicateTo rep) {

    OperationFuture<CASResponse> casOp = asyncCAS(key, cas, value);
    CASResponse casr = null;
    try {
      casr = casOp.get();
    } catch (InterruptedException e) {
      casr = CASResponse.EXISTS;
    } catch (ExecutionException e) {
      casr = CASResponse.EXISTS;
    }
    if (casr != CASResponse.OK) {
      return casr;
    }
    try {
      observePoll(key, casOp.getCas(), req, rep);
    } catch (ObservedException e) {
      casr = CASResponse.OBSERVE_ERROR_IN_ARGS;
    } catch (ObservedTimeoutException e) {
      casr = CASResponse.OBSERVE_TIMEOUT;
    } catch (ObservedModifiedException e) {
      casr = CASResponse.OBSERVE_MODIFIED;
    }
    return casr;
  }
  public CASResponse cas(String key, long casv,
          String value, PersistTo req) {
    return cas(key, casv, value, req, ReplicateTo.ZERO);
  }

