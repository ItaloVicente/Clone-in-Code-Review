	@Test
	public void testBinaryDelta() throws Exception {
		checkBinary("delta", true);
	}

	@Test
	public void testBinaryLiteral() throws Exception {
		checkBinary("literal", true);
	}

	@Test
	public void testBinaryLiteralAdd() throws Exception {
		checkBinary("literal_add", false);
	}

