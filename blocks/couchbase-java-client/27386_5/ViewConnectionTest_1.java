  @Test
  public void testViewConnRefused() throws IOException, InterruptedException {
    List<ViewNode> ln = new ArrayList<ViewNode>();
    try {
      ViewConnection vconn = createViewConn(TestConfig.IPV4_ADDR,2343);
      ln = vconn.getConnectedNodes();
      assertTrue("Something is wrong", false);
    } catch (ConfigurationException e) {
      assertTrue(ln.isEmpty());
      assertEquals("Configuration for bucket \"default\" was not found in server list " +
        "([http://127.0.0.1:2343/pools]).", e.getMessage());
    } catch (Exception e) {
        assertTrue(ln.isEmpty());
        assertEquals("Configuration for bucket \"default\" was not found in server list " +
          "([http://127.0.0.1:2343/pools]).", e.getMessage());
    }
