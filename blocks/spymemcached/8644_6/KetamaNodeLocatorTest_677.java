    Collection<MemcachedNode> all = locator.getAll();
    assertEquals(4, all.size());
    for (int i = 0; i < 4; i++) {
      assertTrue(all.contains(nodes[i]));
    }
  }
