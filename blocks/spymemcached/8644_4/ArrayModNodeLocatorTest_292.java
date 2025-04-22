  public void testAll() throws Exception {
    setupNodes(4);
    Collection<MemcachedNode> all = locator.getAll();
    assertEquals(4, all.size());
    assertTrue(all.contains(nodes[0]));
    assertTrue(all.contains(nodes[1]));
    assertTrue(all.contains(nodes[2]));
    assertTrue(all.contains(nodes[3]));
  }
