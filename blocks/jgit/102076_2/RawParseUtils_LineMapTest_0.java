	@Test
	public void testBinary() throws UnsupportedEncodingException {
		final byte[] buf = "xxxfoo\nb\0ar".getBytes("ISO-8859-1");
		final IntList map = RawParseUtils.lineMap(buf
		System.err.println(map);
		assertArrayEquals(new int[]{Integer.MIN_VALUE
	}

