  private MemcachedClient mc = null;

  SASLConnectReconnect(String username, String password, String host) {

    AuthDescriptor ad =
        new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(
            username, password));
    try {
      List<InetSocketAddress> addresses = AddrUtil.getAddresses(host);
      mc = new MemcachedClient(new ConnectionFactoryBuilder()
          .setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build(),
          addresses);
    } catch (IOException ex) {
      System.err.println("Couldn't create a connection, bailing out:\n"
          + "IOException " + ex.getMessage());
      if (mc != null) {
        mc.shutdown();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 4) {
      System.err.println("Usage example:\nQuickAuthLoad user password"
          + " localhost:11212 10000");
      System.exit(1);
    }
    SASLConnectReconnect m =
        new SASLConnectReconnect(args[0], args[1], args[2]);
