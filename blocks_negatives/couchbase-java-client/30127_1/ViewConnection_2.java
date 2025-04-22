public class ViewConnection extends SpyObject implements
  Reconfigurable {
  private static final int NUM_CONNS = 1;
  private static final int MAX_ADDOP_RETRIES = 6;

  private volatile boolean shutDown = false;
  protected volatile boolean reconfiguring = false;
  protected volatile boolean running = true;

  private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
  private final Lock rlock = rwlock.readLock();
  private final Lock wlock = rwlock.writeLock();

  private final CouchbaseConnectionFactory connFactory;
  private final Collection<ConnectionObserver> connObservers =
    new ConcurrentLinkedQueue<ConnectionObserver>();
  private List<ViewNode> couchNodes;
  private int nextNode;

  /**
   * Kickstarts the initialization and delegates the connection creation.
   *
   * @param cf the factory which contains neeeded information.
   * @param addrs the list of addresses to connect to.
   * @param obs the connection observers.
   * @throws IOException
   */
  public ViewConnection(CouchbaseConnectionFactory cf,
      List<InetSocketAddress> addrs, Collection<ConnectionObserver> obs)
    throws IOException {
    connFactory = cf;
    connObservers.addAll(obs);
    couchNodes = createConnections(addrs);
    nextNode = 0;
  }

  /**
   * Create ViewNode connections and queue them up for connect.
   *
   * This method also defines the connection params for each connection,
   * including the default settings like timeouts and the user agent string.
   *
   * @param addrs addresses of all the nodes it should connect to.
   * @return Returns a list of the ViewNodes.
   * @throws IOException
   */
  private List<ViewNode> createConnections(List<InetSocketAddress> addrs)
    throws IOException {

    List<ViewNode> nodeList = new LinkedList<ViewNode>();

    for (InetSocketAddress a : addrs) {
      HttpParams params = new SyncBasicHttpParams();
      params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
          .setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000)
          .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
          .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,
              false)
          .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
          .setParameter(CoreProtocolPNames.USER_AGENT,
              "Couchbase Java Client 1.0.2");

      HttpProcessor httpproc =
          new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
            new RequestContent(), new RequestTargetHost(),
            new RequestConnControl(), new RequestUserAgent(),
            new RequestExpectContinue(), });

      AsyncNHttpClientHandler protocolHandler =
          new AsyncNHttpClientHandler(httpproc,
              new MyHttpRequestExecutionHandler(this),
              new DefaultConnectionReuseStrategy(),
              new DirectByteBufferAllocator(), params);
      protocolHandler.setEventListener(new EventLogger());

      AsyncConnectionManager connMgr =
          new AsyncConnectionManager(
              new HttpHost(a.getHostName(), a.getPort()), NUM_CONNS,
              protocolHandler, params, new RequeueOpCallback(this));
      getLogger().info("Added %s to connect queue", a.getHostName());

      ViewNode node = connFactory.createViewNode(a, connMgr);
      node.init();
      nodeList.add(node);
    }

    return nodeList;
  }

  /**
   * Write an operation to the next ViewNode.
   *
   * To make sure that the operations are distributed throughout the cluster,
   * the ViewNode is changed every time a new operation is added. Since the
   * getNextNode() method increments the ViewNode IDs and calculates the
   * modulo, the nodes are selected in a round-robin fashion.
   *
   * @param op the operation to run.
   */
  public void addOp(final HttpOperation op) {
    rlock.lock();
    try {
      if (couchNodes.isEmpty()) {
        getLogger().error("No server connections. Cancelling op.");
        op.cancel();
      } else {
        boolean success = false;
        int retries = 0;
        do {
          if(retries >= MAX_ADDOP_RETRIES) {
            op.cancel();
            break;
          }
          ViewNode node = couchNodes.get(getNextNode());
          if(node.isShuttingDown() || !hasActiveVBuckets(node)) {
            continue;
          }
          if(retries > 0) {
            getLogger().debug("Retrying view operation #" + op.hashCode()
              + " on node: " + node.getSocketAddress());
          }
          success = node.writeOp(op);
          if(retries > 0 && success) {
            getLogger().debug("Successfully wrote #" + op.hashCode()
              + " on node: " + node.getSocketAddress() + " after "
              + retries + " retries.");
          }
          retries++;
        } while(!success);
      }
    } finally {
      rlock.unlock();
    }
  }

  /**
   * Calculates the next node to run the operation on.
   *
   * @return id of the next node.
   */
  private int getNextNode() {
    return nextNode = (++nextNode % couchNodes.size());
  }
