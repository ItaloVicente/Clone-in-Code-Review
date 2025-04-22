  private final ConnectingIOReactor ioReactor;

  private final BasicNIOConnPool pool;

  private final HttpAsyncRequester requester;

  private final String username;

  private final String password;

  private volatile Thread reactorThread;

  private volatile boolean running;

