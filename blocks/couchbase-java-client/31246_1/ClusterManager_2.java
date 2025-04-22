  private final BasicNIOConnPool pool;

  private final HttpAsyncRequester requester;

  private final String username;

  private final String password;

  private volatile Thread reactorThread;
