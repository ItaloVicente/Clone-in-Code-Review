  public SASLBaseOperationImpl(int c, String[] m, byte[] ch, String s,
      Map<String, ?> p, CallbackHandler h, OperationCallback cb) {
    super(c, generateOpaque(), cb);
    mech = m;
    challenge = ch;
    serverName = s;
    props = p;
    cbh = h;
  }
