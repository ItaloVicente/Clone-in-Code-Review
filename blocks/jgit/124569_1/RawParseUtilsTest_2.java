	@Test
	public void testHeaderStart() {
		byte[] headerName = "some".getBytes(UTF_8);
		byte[] commitBytes = commit.getBytes(UTF_8);
		assertEquals(625
		assertEquals(625

		byte[] missingHeaderName = "missing".getBytes(UTF_8);
		assertEquals(-1
							   commitBytes

		byte[] fauxHeaderName = "other".getBytes(UTF_8);
		assertEquals(-1
	}

	@Test
	public void testNextHeader() {
		byte[] commitBytes = commit.getBytes(UTF_8);
		int[] expected = new int[] {46
		int start = 0;
		for (int i = 0; i < expected.length; i++) {
			start = RawParseUtils.nextHeader(commitBytes
			assertEquals(expected[i]
		}
	}
