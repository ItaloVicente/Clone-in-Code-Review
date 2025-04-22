	public void testMarkSupported() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			assertFalse(u.markSupported());
			u.add(new ByteArrayInputStream(new byte[] { 1
			assertFalse(u.markSupported());
		}
