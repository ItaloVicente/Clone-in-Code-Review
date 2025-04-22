  public ClusterManager(List<URI> uris, String username, String password) {
    addrs = uris;
    user = username;
    pass = password;

    httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
      new RequestContent(), new RequestTargetHost(),
      new RequestConnControl(), new RequestUserAgent(),
      new RequestExpectContinue()});

    httpexecutor = new HttpRequestExecutor();
    context = new BasicHttpContext(null);
    conn = new DefaultHttpClientConnection();
