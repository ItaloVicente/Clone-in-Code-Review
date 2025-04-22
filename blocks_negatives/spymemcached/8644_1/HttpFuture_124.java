public class HttpFuture<T> extends SpyObject implements Future<T>{
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
