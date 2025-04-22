	@Test
	public void testBinaryDetect() throws IOException {
		final byte[] bytes = asBytes("1\r\n2\r\n3\0");
		test(bytes
	}

	@Test
	public void testBinaryDontDetect() throws IOException {
		test(asBytes("1\r\n2\r\n3\0")
	}

	private void test(byte[] input
