  public void testPrimary() throws Exception {
    setupNodes(4);
    assertSame(nodes[3], locator.getPrimary("dustin"));
    assertSame(nodes[0], locator.getPrimary("x"));
    assertSame(nodes[1], locator.getPrimary("y"));
  }
