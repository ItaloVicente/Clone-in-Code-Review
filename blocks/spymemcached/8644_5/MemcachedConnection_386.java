public final class MemcachedConnection extends SpyThread implements
    Reconfigurable {

  private static final int DOUBLE_CHECK_EMPTY = 256;
  private static final int EXCESSIVE_EMPTY = 0x1000000;

  private volatile boolean shutDown = false;
  private final boolean shouldOptimize;
  private Selector selector = null;
  private final NodeLocator locator;
  private final FailureMode failureMode;
  private final long maxDelay;
  private int emptySelects = 0;
  private final int bufSize;
  private final ConnectionFactory connectionFactory;
  private final ConcurrentLinkedQueue<MemcachedNode> addedQueue;
  private final SortedMap<Long, MemcachedNode> reconnectQueue;

  protected volatile boolean reconfiguring = false;
  protected volatile boolean running = true;

  private final Collection<ConnectionObserver> connObservers =
      new ConcurrentLinkedQueue<ConnectionObserver>();
  private final OperationFactory opFact;
  private final int timeoutExceptionThreshold;
  private final Collection<Operation> retryOps;
  private final ConcurrentLinkedQueue<MemcachedNode> nodesToShutdown;

  public MemcachedConnection(int bufSize, ConnectionFactory f,
      List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
      FailureMode fm, OperationFactory opfactory) throws IOException {
    connObservers.addAll(obs);
    reconnectQueue = new TreeMap<Long, MemcachedNode>();
    addedQueue = new ConcurrentLinkedQueue<MemcachedNode>();
    failureMode = fm;
    shouldOptimize = f.shouldOptimize();
    maxDelay = f.getMaxReconnectDelay();
    opFact = opfactory;
    timeoutExceptionThreshold = f.getTimeoutExceptionThreshold();
    selector = Selector.open();
    retryOps = new ArrayList<Operation>();
    nodesToShutdown = new ConcurrentLinkedQueue<MemcachedNode>();
    this.bufSize = bufSize;
    this.connectionFactory = f;
    List<MemcachedNode> connections = createConnections(a);
    locator = f.createLocator(connections);
    setName("Memcached IO over " + this);
    setDaemon(f.isDaemon());
    start();
  }

  private List<MemcachedNode> createConnections(
      final Collection<InetSocketAddress> a) throws IOException {
    List<MemcachedNode> connections = new ArrayList<MemcachedNode>(a.size());
    for (SocketAddress sa : a) {
      SocketChannel ch = SocketChannel.open();
      ch.configureBlocking(false);
      MemcachedNode qa =
          this.connectionFactory.createMemcachedNode(sa, ch, bufSize);
      int ops = 0;
      ch.socket().setTcpNoDelay(!this.connectionFactory.useNagleAlgorithm());
      try {
        if (ch.connect(sa)) {
          getLogger().info("Connected to %s immediately", qa);
          connected(qa);
        } else {
          getLogger().info("Added %s to connect queue", qa);
          ops = SelectionKey.OP_CONNECT;
