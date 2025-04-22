  public void testPrimaryClone() throws Exception {
    setupNodes(4);
    assertEquals(nodes[3].toString(),
        locator.getReadonlyCopy().getPrimary("dustin").toString());
    assertEquals(nodes[0].toString(), locator.getReadonlyCopy().getPrimary("x")
        .toString());
    assertEquals(nodes[1].toString(), locator.getReadonlyCopy().getPrimary("y")
        .toString());
  }
