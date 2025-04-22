  @Test
  public void testViewConnRefused() throws IOException, InterruptedException {
    try {
      ViewConnection vconn = createViewConn(TestConfig.IPV4_ADDR,2343);
      assertTrue(vconn.getConnectedNodes().isEmpty());
      vconn.shutdown();
      assertTrue(vconn.getConnectedNodes().isEmpty());
    } catch (Exception e) {
      assertFalse(e.getMessage().isEmpty());
    }
