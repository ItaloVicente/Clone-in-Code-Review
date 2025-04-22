public class TapConnectionProvider extends SpyObject implements
    ConnectionObserver, Reconfigurable {

  private volatile boolean shuttingDown = false;

  private final CouchbaseConnection conn;

  private final OperationFactory opFact;

  private final TranscodeService tcService;

  private final AuthDescriptor authDescriptor;

  private final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

  private ConfigurationProvider configurationProvider;

  /**
   * Get a memcache client operating on the specified memcached locations.
   *
   * @param ia the memcached locations
   * @throws IOException if connections cannot be established
   */
  public TapConnectionProvider(InetSocketAddress... ia) throws IOException {
    this(new BinaryConnectionFactory(), Arrays.asList(ia));
  }

  /**
   * Get a memcache client over the specified memcached locations.
   *
   * @param addrs the socket addrs
   * @throws IOException if connections cannot be established
   */
  public TapConnectionProvider(List<InetSocketAddress> addrs)
    throws IOException {
    this(new BinaryConnectionFactory(), addrs);
  }

  /**
   * Get a memcache client over the specified memcached locations.
   *
   * @param cf the connection factory to configure connections for this client
   * @param addrs the socket addresses
   * @throws IOException if connections cannot be established
   */
  private TapConnectionProvider(ConnectionFactory cf,
      List<InetSocketAddress> addrs) throws IOException {
    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException(
          "You must have at least one server to connect to");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    tcService = new TranscodeService(cf.isDaemon());
    cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    conn = (CouchbaseConnection)cf.createConnection(addrs);
    assert conn != null : "Connection factory failed to make a connection";
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
    }
  }
