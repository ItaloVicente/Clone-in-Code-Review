	private TestOperation buildOp(int portNum) {
		TestOperation op = new TestOperation();
		MockMemcachedNode node = new MockMemcachedNode(
				InetSocketAddress.createUnresolved(TestConfig.IPV4_ADDR, portNum));
		op.setHandlingNode(node);
		return op;
	}
