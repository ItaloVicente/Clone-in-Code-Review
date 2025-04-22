    this.user = user;
    this.password = password;

    httpProc = HttpProcessorBuilder.create()
      .add(new RequestContent())
      .add(new RequestTargetHost())
      .add(new RequestConnControl())
      .add(new RequestUserAgent("JCBC/1.2"))
      .add(new RequestExpectContinue(true)).build();

    HttpAsyncRequestExecutor protocolHandler = new HttpAsyncRequestExecutor();

    ioEventDispatch = new DefaultHttpClientIODispatch(protocolHandler,
      ConnectionConfig.DEFAULT);

    ioReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom()
      .setConnectTimeout(5000)
      .setSoTimeout(5000)
      .setTcpNoDelay(true)
      .build());

    pool = new BasicNIOConnPool(ioReactor, ConnectionConfig.DEFAULT);

    pool.setDefaultMaxPerRoute(2);
    pool.setMaxTotal(2);

    requester = new HttpAsyncRequester(httpProc);

    init();
  }

  public void init() {
    ioThread = new Thread(new Runnable() {
      public void run() {
        try {
          ioReactor.execute(ioEventDispatch);
        } catch (InterruptedIOException ex) {
          getLogger().error("I/O reactor Interrupted", ex);
        } catch (IOException e) {
          getLogger().error("I/O error: " + e.getMessage(), e);
        }
        getLogger().info("I/O reactor terminated for ");
      }
    }, "Couchbase View Thread");
    ioThread.start();
