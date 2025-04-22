  public void testGetVersions() {
    Map<SocketAddress, String> vs = ((CouchbaseClient)client).getVersions();
    System.out.println(vs);
    assertEquals(client.getAvailableServers().size(), vs.size());
  }

  public void testGetStats() throws Exception {
    Map<SocketAddress, Map<String, String>> stats = ((CouchbaseClient)client).getStats();
    assertEquals(client.getAvailableServers().size(), stats.size());
    Map<String, String> oneStat = stats.values().iterator().next();
    assertTrue(oneStat.containsKey("curr_items"));
  }


