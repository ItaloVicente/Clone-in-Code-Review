    viewConnection = conn;

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

    host = new HttpHost(addr.getHostName(), addr.getPort(), "http");
    requester = new HttpAsyncRequester(httpProc);

