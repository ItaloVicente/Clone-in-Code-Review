	public void testNullOperation() {
		assertEquals(CheckedOperationTimeoutException.class.getName()
				+ ": test - failing node: <unknown>",
				new CheckedOperationTimeoutException("test",
						(Operation)null).toString());
	}
