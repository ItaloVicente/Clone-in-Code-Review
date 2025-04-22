	public void testSingleOperation() {
		Operation op = buildOp(11211);
		assertEquals(CheckedOperationTimeoutException.class.getName()
				+ ": test - failing node: " + TestConfig.IPV4_ADDR + ":11211",
				new CheckedOperationTimeoutException("test", op).toString());
	}

	public void testNullNode() {
		Operation op = new TestOperation();
		assertEquals(CheckedOperationTimeoutException.class.getName()
				+ ": test - failing node: <unknown>",
				new CheckedOperationTimeoutException("test", op).toString());
	}
