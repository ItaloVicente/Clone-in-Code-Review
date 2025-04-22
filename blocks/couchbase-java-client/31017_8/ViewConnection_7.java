  public ViewConnection(final CouchbaseConnectionFactory cf,
    final List<InetSocketAddress> addrs, final String user,
    final String password) throws IOException {
    connFactory = cf;
    nextNode = 0;
    this.user = user;
    this.password = password;

    viewNodes = Collections.synchronizedList(new ArrayList<HttpHost>());
    for (InetSocketAddress addr : addrs) {
      viewNodes.add(createHttpHost(addr.getHostName(), addr.getPort()));
