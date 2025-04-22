  private static final String SERVER_URI = "http://" + TestConfig.IPV4_ADDR
      + ":8091/pools";

  protected static TestingClient client = null;

  protected static void initClient() throws Exception {
    List<URI> uris = new LinkedList<URI>();
    uris.add(URI.create(SERVER_URI));
    client = new TestingClient(uris, "default", "");
  }

  @BeforeClass
  public static void before() throws Exception {
