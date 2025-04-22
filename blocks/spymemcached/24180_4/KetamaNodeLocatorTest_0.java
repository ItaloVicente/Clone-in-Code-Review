  public void testAllAfterUpdate() throws Exception {
     setupNodes(4);

    Collection<MemcachedNode> all = locator.getAll();
    assertEquals(4, all.size());
    for (int i = 0; i < 4; i++) {
      assertTrue(all.contains(nodes[i]));
    }

    ArrayList<MemcachedNode> toUpdate = new ArrayList<MemcachedNode>();
    Mock mock = mock(MemcachedNode.class);
    mock.expects(atLeastOnce()).method("getSocketAddress")
          .will(returnValue(InetSocketAddress.createUnresolved("127.0.0.1",
          10000)));
    toUpdate.add((MemcachedNode) mock.proxy());
    locator.updateLocator(toUpdate);

    Collection<MemcachedNode> afterUpdate = locator.getAll();
    assertEquals(1, afterUpdate.size());
    for (int i = 0; i < 4; i++) {
      assertFalse(afterUpdate.contains(nodes[i]));
    }
    assertTrue(afterUpdate.contains((MemcachedNode) mock.proxy()));
  }

