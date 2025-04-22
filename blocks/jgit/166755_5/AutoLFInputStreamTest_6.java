	@Test
	public void testCrLf() throws IOException {
		test(asBytes("1\r\n2\n3\r\n\r")
				"1\r\n2\r\n3\r\n\r")
				in -> new AutoLFInputStream(true
	}

	@Test
	public void testCrLfDontDetect() throws IOException {
		test(asBytes("1\r\n2\r\n")
				in -> new AutoLFInputStream(true
	}

