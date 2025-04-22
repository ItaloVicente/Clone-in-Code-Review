  private static final int NUM_CONNS = 1;

  private final CouchbaseConnectionFactory connFactory;
  private final ConcurrentLinkedQueue<ViewNode> nodesToShutdown;
  private List<ViewNode> nodes;

  public CouchbaseConnection(CouchbaseConnectionFactory cf,
      List<InetSocketAddress> addrs, Collection<ConnectionObserver> obs)
    throws IOException {
    super(cf.getReadBufSize(), cf, addrs, obs, cf.getFailureMode(), cf.getOperationFactory());
    shutDown = false;
    connFactory = cf;
    nodesToShutdown = new ConcurrentLinkedQueue<ViewNode>();
    nodes = createConnections(addrs);
  }

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
            "Spymemcached Client/1.1");

    HttpProcessor httpproc =
        new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
          new RequestContent(), new RequestTargetHost(),
          new RequestConnControl(), new RequestUserAgent(),
          new RequestExpectContinue(), });

    AsyncNHttpClientHandler protocolHandler =
        new AsyncNHttpClientHandler(httpproc,
            new MyHttpRequestExecutionHandler(),
            new DefaultConnectionReuseStrategy(),
            new DirectByteBufferAllocator(), params);
    protocolHandler.setEventListener(new EventLogger());
