  public void testGetStatsCacheDump() throws Exception {
    if (isMoxi()) {
      return;
    }
    client.set("dumpinitializer", 0, "hi");
    Map<SocketAddress, Map<String, String>> stats =
        client.getStats("cachedump 1 10000");
    System.out.println("Stats cachedump:  " + stats);
    assertEquals(1, stats.size());
    Map<String, String> oneStat = stats.values().iterator().next();
    String val = oneStat.get("dumpinitializer");
    assertTrue(val + "doesn't match", val.matches("\\[2 b; \\d+ s\\]"));
  }

