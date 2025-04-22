    ViewConnection vconn = createViewConn(TestConfig.IPV4_ADDR,8091);
    assertFalse(vconn.getConnectedNodes().isEmpty());
    vconn.shutdown();
    assertTrue(vconn.getConnectedNodes().isEmpty());

  }

  private ViewConnection createViewConn(String host, int port) throws IOException {
