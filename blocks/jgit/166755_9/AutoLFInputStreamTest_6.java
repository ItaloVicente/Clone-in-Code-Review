	@Test
	public void testCrLf() throws IOException {
		byte[] bytes = asBytes("1\r\n2\n3\r\n\r");
		test(bytes
				EnumSet.of(StreamFlag.DETECT_BINARY
	}

	@Test
	public void testCrLfDontDetect() throws IOException {
		test(asBytes("1\r\n2\r\n")
				in -> new AutoLFInputStream(in
						EnumSet.of(StreamFlag.DETECT_BINARY)));
	}

