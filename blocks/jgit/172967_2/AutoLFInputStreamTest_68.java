		test(asBytes("1\r\n2\r\n3\0")
	}

	@Test
	public void testCrLf() throws IOException {
		byte[] bytes = asBytes("1\r\n2\n3\r\n\r");
		test(bytes
				StreamFlag.DETECT_BINARY
	}

	@Test
	public void testCrLfDontDetect() throws IOException {
		test(asBytes("1\r\n2\r\n")
				in -> AutoLFInputStream.create(in
	}

	private static void test(byte[] input
			throws IOException {
		test(input
