public class HttpFuture<T> extends SpyObject implements Future<T> {
  private final AtomicReference<T> objRef;
  private final CountDownLatch latch;
  private final long timeout;
  private OperationStatus status;
  private HttpOperation op;

  public HttpFuture(CountDownLatch latch, long timeout) {
    super();
    this.objRef = new AtomicReference<T>(null);
    this.latch = latch;
    this.timeout = timeout;
  }

  public boolean cancel(boolean c) {
    op.cancel();
    return true;
  }

  @Override
  public T get() throws InterruptedException, ExecutionException {
    try {
      return get(timeout, TimeUnit.MILLISECONDS);
    } catch (TimeoutException e) {
      status = new OperationStatus(false, "Timed out");
      throw new RuntimeException("Timed out waiting for operation", e);
    }
  }

  @Override
  public T get(long duration, TimeUnit units) throws InterruptedException,
      ExecutionException, TimeoutException {
    if (!latch.await(duration, units)) {
      if (op != null) {
        op.timeOut();
      }
      status = new OperationStatus(false, "Timed out");
      throw new TimeoutException("Timed out waiting for operation");
