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
      .setIoThreadCount(DEFAULT_IO_THREAD_COUNT)
      .build());

    pool = new BasicNIOConnPool(ioReactor, ConnectionConfig.DEFAULT);
    pool.setDefaultMaxPerRoute(DEFAULT_MAX_CONNS_PER_NODE);
    updateMaxTotalRequests();

    initializeReactorThread();
