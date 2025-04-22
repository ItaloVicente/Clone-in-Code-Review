  public ClusterManager(List<URI> nodes, String username, String password,
    int connectionTimeout, int socketTimeout, boolean tcpNoDelay,
    int ioThreadCount, int connectionsPerNode) {
    if (nodes == null || nodes.isEmpty()) {
      throw new IllegalArgumentException("List of nodes is null or empty");
    }
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username is null or empty");
    }
    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password is null or empty");
    }

    this.username = username;
    this.password = password;

    clusterNodes = Collections.synchronizedList(new ArrayList<HttpHost>());
    for (URI node : nodes) {
      clusterNodes.add(new HttpHost(node.getHost(), node.getPort()));
    }

    HttpProcessor httpProc = HttpProcessorBuilder.create()
      .add(new RequestContent())
      .add(new RequestTargetHost())
      .add(new RequestConnControl())
      .add(new RequestUserAgent("JCBC/1.2"))
      .add(new RequestExpectContinue(true)).build();

    requester = new HttpAsyncRequester(httpProc);

