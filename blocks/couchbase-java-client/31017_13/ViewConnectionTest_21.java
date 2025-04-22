    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    Map<HttpHost, Integer> hostCounts = new HashMap<HttpHost, Integer>();
    for (HttpHost host : conn.getConnectedHosts()) {
      hostCounts.put(host, 0);
    }

    for (int i = 0; i < 40; i++) {
      HttpHost host = conn.getNextNode();
      hostCounts.put(host, hostCounts.get(host) + 1);
    }

    for (HttpHost host : conn.getConnectedHosts()) {
      assertEquals(10, hostCounts.get(host).intValue());
    }
  }

  @Test
  public void shouldCancelOperationIfNoHostsInPlace() throws Exception {
    List<InetSocketAddress> initialNodes = Collections.emptyList();

    ViewConnection conn = new ViewConnection(factoryMock, initialNodes,
      DEFAULT_USER, DEFAULT_PASS);

    HttpOperation operationMock = mock(HttpOperation.class);
    conn.addOp(operationMock);
    verify(operationMock).cancel();
  }

  @Test
  public void shouldShutdownCleanly() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT),
      new InetSocketAddress("10.0.0.3", PORT),
      new InetSocketAddress("10.0.0.4", PORT)
