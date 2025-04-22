  @Test
  public void testNetworkUnreachable() throws IOException,InterruptedException {
    try {
      ViewConnection vconn = createViewConn("10.34.34.23",8091);
      assertTrue(vconn.getConnectedNodes().isEmpty());
      vconn.shutdown();
      assertTrue(vconn.getConnectedNodes().isEmpty());
    } catch (Exception e) {
      assertFalse(e.getMessage().isEmpty());
    }
  }
}
