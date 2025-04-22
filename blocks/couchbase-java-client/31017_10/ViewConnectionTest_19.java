  private static final String DEFAULT_USER = "default";
  private static final String DEFAULT_PASS = "";
  private static final int PORT = 8092;

  private CouchbaseConnectionFactory factoryMock;
  private Bucket bucketMock;
  private DefaultConfig configMock;

  @Before
  public void setup() {
    factoryMock = mock(CouchbaseConnectionFactory.class);
    when(factoryMock.getViewWorkerSize()).thenReturn(1);
    when(factoryMock.getViewConnsPerNode()).thenReturn(1);

    bucketMock = mock(Bucket.class);
    configMock = mock(DefaultConfig.class);

    when(bucketMock.getConfig()).thenReturn(configMock);
    when(configMock.nodeHasActiveVBuckets(any(InetSocketAddress.class)))
      .thenReturn(true);
  }

  @Test
  public void shouldInitializeWithHosts() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT)
    );

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(2, connected.size());
    assertEquals("10.0.0.1:" + PORT, connected.get(0).toHostString());
    assertEquals("10.0.0.2:" + PORT, connected.get(1).toHostString());
  }

  @Test
  public void shouldAddHostsOnRebalance() throws Exception {
    List<InetSocketAddress> initialNodes = Collections.singletonList(
      new InetSocketAddress("10.0.0.1", PORT));

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);


    List<URL> viewServers = Arrays.asList(
      new URL("http://10.0.0.1:" + PORT),
      new URL("http://10.0.0.2:" + PORT),
      new URL("http://10.0.0.3:" + PORT)
    );
    when(configMock.getCouchServers()).thenReturn(viewServers);

    assertEquals(1, conn.getConnectedHosts().size());
    conn.reconfigure(bucketMock);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(3, conn.getConnectedHosts().size());
    assertEquals("10.0.0.1:" + PORT, connected.get(0).toHostString());
    assertEquals("10.0.0.2:" + PORT, connected.get(1).toHostString());
    assertEquals("10.0.0.3:" + PORT, connected.get(2).toHostString());
  }

  @Test
  public void shouldRemoveHostsOnRebalance() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT),
      new InetSocketAddress("10.0.0.3", PORT)
    );

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);


    List<URL> viewServers = Collections.singletonList(
      new URL("http://10.0.0.1:" + PORT));
    when(configMock.getCouchServers()).thenReturn(viewServers);

    assertEquals(3, conn.getConnectedHosts().size());
    conn.reconfigure(bucketMock);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(1, conn.getConnectedHosts().size());
    assertEquals("10.0.0.1:" + PORT, connected.get(0).toHostString());
  }

  @Test
  public void shouldAddAndRemoveNodeOnRebalance() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT)
    );

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    List<URL> viewServers = Arrays.asList(
      new URL("http://10.0.0.3:" + PORT),
      new URL("http://10.0.0.4:" + PORT)
    );
    when(configMock.getCouchServers()).thenReturn(viewServers);

    assertEquals(2, conn.getConnectedHosts().size());
    conn.reconfigure(bucketMock);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(2, conn.getConnectedHosts().size());
    assertEquals("10.0.0.3:" + PORT, connected.get(0).toHostString());
    assertEquals("10.0.0.4:" + PORT, connected.get(1).toHostString());
  }

  @Test
  public void shouldNotAddIfNoActiveVBucket() throws Exception {
    List<InetSocketAddress> initialNodes = Collections.singletonList(
      new InetSocketAddress("10.0.0.1", PORT));

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    when(configMock.nodeHasActiveVBuckets(
      new InetSocketAddress("10.0.0.1", PORT))
    ).thenReturn(true);
    when(configMock.nodeHasActiveVBuckets(
      new InetSocketAddress("10.0.0.2", PORT))
    ).thenReturn(true);
    when(configMock.nodeHasActiveVBuckets(
      new InetSocketAddress("10.0.0.3", PORT))
    ).thenReturn(false);

    List<URL> viewServers = Arrays.asList(
      new URL("http://10.0.0.1:" + PORT),
      new URL("http://10.0.0.2:" + PORT),
      new URL("http://10.0.0.3:" + PORT)
    );
    when(configMock.getCouchServers()).thenReturn(viewServers);

    assertEquals(1, conn.getConnectedHosts().size());
    conn.reconfigure(bucketMock);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(2, conn.getConnectedHosts().size());
    assertEquals("10.0.0.1:" + PORT, connected.get(0).toHostString());
    assertEquals("10.0.0.2:" + PORT, connected.get(1).toHostString());
  }

  @Test
  public void shouldRemoveIfNoActiveVBucket() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT)
    );

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    when(configMock.nodeHasActiveVBuckets(
      new InetSocketAddress("10.0.0.1", PORT))
    ).thenReturn(true);
    when(configMock.nodeHasActiveVBuckets(
      new InetSocketAddress("10.0.0.2", PORT))
    ).thenReturn(false);

    List<URL> viewServers = Arrays.asList(
      new URL("http://10.0.0.1:" + PORT),
      new URL("http://10.0.0.2:" + PORT)
    );
    when(configMock.getCouchServers()).thenReturn(viewServers);

    assertEquals(2, conn.getConnectedHosts().size());
    conn.reconfigure(bucketMock);

    List<HttpHost> connected = conn.getConnectedHosts();
    assertEquals(1, conn.getConnectedHosts().size());
    assertEquals("10.0.0.1:" + PORT, connected.get(0).toHostString());
  }

