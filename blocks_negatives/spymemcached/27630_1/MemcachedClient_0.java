    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException("You must have at least one server to"
          + " connect to");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    connFactory = cf;
    tcService = new TranscodeService(cf.isDaemon());
    transcoder = cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    mconn = cf.createConnection(addrs);
    assert mconn != null : "Connection factory failed to make a connection";
    operationTimeout = cf.getOperationTimeout();
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
