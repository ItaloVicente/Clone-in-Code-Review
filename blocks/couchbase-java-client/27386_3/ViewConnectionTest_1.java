  @Test
  public void testViewConnRefused() throws IOException, InterruptedException {
      List<ViewNode> ln = new ArrayList<ViewNode>();
      try {
          ViewConnection vconn = createViewConn(TestConfig.IPV4_ADDR,8091);
          ln = vconn.getConnectedNodes();
      } catch (ConfigurationException e) {
          assertTrue(ln.isEmpty());
          assertEquals("Configuration for bucket \"default\" was not found in server list " +
                  "([http://10.34.34.23:8091/pools]).", e.getMessage());
      }
