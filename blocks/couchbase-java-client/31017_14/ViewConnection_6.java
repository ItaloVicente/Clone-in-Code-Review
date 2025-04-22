  public ViewConnection(final CouchbaseConnectionFactory cf,
    final List<InetSocketAddress> seedAddrs, final String user,
    final String password) throws IOException {
    nextNode = 0;
    this.user = user;
    this.password = password;

    viewNodes = Collections.synchronizedList(new ArrayList<HttpHost>());
    for (InetSocketAddress addr : seedAddrs) {
      viewNodes.add(createHttpHost(addr.getHostName(), addr.getPort()));
    }

    HttpProcessor httpProc = HttpProcessorBuilder.create()
      .add(new RequestContent())
      .add(new RequestTargetHost())
      .add(new RequestConnControl())
      .add(new RequestUserAgent("JCBC/1.2"))
      .add(new RequestExpectContinue(true)).build();

    requester = new HttpAsyncRequester(httpProc);

    ioReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom()
      .setConnectTimeout(5000)
      .setSoTimeout(5000)
      .setTcpNoDelay(true)
      .setIoThreadCount(cf.getViewWorkerSize())
      .build());

    pool = new ViewPool(ioReactor, ConnectionConfig.DEFAULT);
    pool.setDefaultMaxPerRoute(cf.getViewConnsPerNode());
    updateMaxTotalRequests();

    initializeReactorThread();
