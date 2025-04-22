  private final String password;

  private final ConnectingIOReactor ioReactor;

  private final BasicNIOConnPool pool;

  private final HttpAsyncRequester requester;

  private volatile Thread reactorThread;

  private volatile int nextNode;

  private volatile boolean running;

