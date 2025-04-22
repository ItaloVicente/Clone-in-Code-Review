  private final Map<String, Future<T>> rvMap;
  private final Collection<Operation> ops;
  private final CountDownLatch latch;
  private OperationStatus status;
  private boolean cancelled = false;
  private boolean timeout = false;

  public BulkGetFuture(Map<String, Future<T>> m, Collection<Operation> getOps,
      CountDownLatch l) {
    super();
    rvMap = m;
    ops = getOps;
    latch = l;
    status = null;
  }
