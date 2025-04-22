  public void shouldRoundRobinRequests() throws Exception {
    List<InetSocketAddress> initialNodes = Arrays.asList(
      new InetSocketAddress("10.0.0.1", PORT),
      new InetSocketAddress("10.0.0.2", PORT),
      new InetSocketAddress("10.0.0.3", PORT),
      new InetSocketAddress("10.0.0.4", PORT)
