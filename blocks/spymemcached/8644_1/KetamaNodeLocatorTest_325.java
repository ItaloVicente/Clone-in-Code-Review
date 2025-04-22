  public void testLookupsClone() {
    setupNodes(4);
    assertSame(nodes[0].toString(),
        locator.getReadonlyCopy().getPrimary("dustin").toString());
    assertSame(nodes[2].toString(),
        locator.getReadonlyCopy().getPrimary("noelani").toString());
    assertSame(nodes[0].toString(),
        locator.getReadonlyCopy().getPrimary("some other key").toString());
  }
