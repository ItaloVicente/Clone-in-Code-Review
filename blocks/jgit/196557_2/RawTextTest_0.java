	@Test
	public void testCrAtLimit() throws Exception {
		int limit = RawText.getBufferSize();
		byte[] data = new byte[RawText.getBufferSize() + 2];
		data[0] = 'A';
		for (int i = 1; i < limit - 1; i++) {
			if (i % 7 == 0) {
				data[i] = '\n';
			} else {
				data[i] = (byte) ('A' + i % 7);
			}
		}
		data[limit - 1] = '\r';
		data[limit] = '\n';
		data[limit + 1] = 'A';
		assertTrue(RawText.isBinary(data
		assertFalse(RawText.isBinary(data
		assertFalse(RawText.isBinary(data
		byte[] buf = Arrays.copyOf(data
		try (ByteArrayInputStream in = new ByteArrayInputStream(buf)) {
			assertTrue(RawText.isBinary(in));
		}
		byte[] buf2 = Arrays.copyOf(data
		try (ByteArrayInputStream in = new ByteArrayInputStream(buf2)) {
			assertFalse(RawText.isBinary(in));
		}
		byte[] buf3 = Arrays.copyOf(data
		try (ByteArrayInputStream in = new ByteArrayInputStream(buf3)) {
			assertFalse(RawText.isBinary(in));
		}
	}

