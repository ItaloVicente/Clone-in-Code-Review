  private final String username;
  private final String password;
  private MemcachedClient client;

  public AuthTest(String u, String p) {
    username = u;
    password = p;
  }

  public void init() throws Exception {
    client = new MemcachedClient(new ConnectionFactoryBuilder()
        .setProtocol(Protocol.BINARY)
        .setAuthDescriptor(AuthDescriptor.typical(username, password))
        .build(), AddrUtil.getAddresses("localhost:11212"));
  }

  public void shutdown() throws Exception {
    client.shutdown();
  }

  public void run() {
    System.out.println("Available mechs:  " + client.listSaslMechanisms());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    client.getVersions();
  }
