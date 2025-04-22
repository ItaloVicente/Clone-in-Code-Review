   * Creates a TapClient against the specified servers.
   *
   * This type of TapClient will TAP the specified servers, but will not be able
   * to react to changes in the number of cluster nodes. Using the constructor
   * which bootstraps itself from the cluster REST interface is preferred.
   *
   * @param ia the addresses of each node in the cluster.
   */
  public TapClient(InetSocketAddress... ia) {
    this(Arrays.asList(ia));
  }

  /**
   * Creates a TapClient against the specified servers.
   *
   * This type of TapClient will TAP the specified servers, but will not be able
   * to react to changes in the number of cluster nodes. Using the constructor
   * which bootstraps itself from the cluster REST interface is preferred.
   *
   * @param addrs a list of addresses containing each node in the cluster.
   */
  public TapClient(List<InetSocketAddress> addrs) {
    this.rqueue = new LinkedBlockingQueue<Object>();
    this.omap = new HashMap<Operation, TapConnectionProvider>();
    this.vBucketAware = false;
    this.addrs = addrs;
    this.baseList = null;
    this.bucketName = null;
    this.usr = null;
    this.pwd = null;
    this.messagesRead = 0;
  }

  /**
   * Creates a cluster aware TapClient
