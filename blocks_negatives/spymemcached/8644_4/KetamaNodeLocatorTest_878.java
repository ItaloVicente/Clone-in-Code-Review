	public void testLookups() {
		setupNodes(4);
		assertSame(nodes[0], locator.getPrimary("dustin"));
		assertSame(nodes[2], locator.getPrimary("noelani"));
		assertSame(nodes[0], locator.getPrimary("some other key"));
	}
