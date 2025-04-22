  private MemcachedClient mc = null;

  SASLConnectReconnect(String username, String password, String host) {

    AuthDescriptor ad =
        new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(
            username, password));
    try {
      List<InetSocketAddress> addresses = AddrUtil.getAddresses(host);
      mc =
          new MemcachedClient(new ConnectionFactoryBuilder()
              .setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build(),
              addresses);
    } catch (IOException ex) {
      System.err
          .println("Couldn't create a connection, bailing out: \nIOException "
              + ex.getMessage());
      if (mc != null) {
        mc.shutdown();
      }
    }
