  private List<HttpHost> viewNodes;
  private final String user;
  private final String password;

  private final HttpProcessor httpProc;
  private final ConnectingIOReactor ioReactor;
  private final BasicNIOConnPool pool;
  private final IOEventDispatch ioEventDispatch;
  private final HttpAsyncRequester requester;
  private Thread ioThread;

